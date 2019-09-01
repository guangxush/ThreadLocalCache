## ThreadLocal是什么？
ThreadLocal是一个本地线程副本变量工具类。主要用于将私有线程和该线程存放的副本对象做一个映射，各个线程之间的变量互不干扰，在高并发场景下，可以实现无状态的调用，特别适用于各个线程依赖不通的变量值完成操作的场景。
对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，形成了副本的隔离，互不干扰。

## Spring请求如何保证线程安全？
Spring的Controller默认是Singleton的，这意味着每个请求过来，系统都会用原有的Instance去处理，这样导致了两个结果:一是我们不用每次创建Controller，二是减少了对象创建和垃圾收集的时间;由于只有一个Controller的instance，当多个线程调用它的时候，它里面的instance变量就不是线程安全的了，会发生窜数据的问题。当然大多数情况下，我们根本不需要考虑线程安全的问题，比如dao,service等，除非在bean中声明了实例变量。

如果控制器是使用单例形式，且Controller中有一个私有的变量a,所有请求到同一个Controller时，使用的a变量是共用的，即若是某个请求中修改了这个变量a，则，在别的请求中能够读到这个修改的内容。Spring使用 ThreadLocal 进行处理，ThreadLocal 是线程本地变量，每个线程拥有变量的一个独立副本，所以各个线程之间互不影响，保证了线程安全。

解决线程安全的方法有以下几个：
1、在Controller中使用ThreadLocal变量
2、在spring配置文件Controller中声明 scope="prototype"，每次都创建新的controller

## 案例分析：
除了解决线程安全问题之外，ThreadLocal还能做什么呢？
场景：如果在一次用户请求中，多次发生查询某一行成绩记录的动作，反复查询相同的结果会对DB造成压力，这里可以将请求保存在ThreadLocal里面，其他查询直接从ThreadLocal中取值即可。

![查询服务](https://upload-images.jianshu.io/upload_images/7632302-a29b65261fbdfea0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 代码实现
根据学号和年级查询成绩信息
```
public interface ScoreRepo extends JpaRepository<Score, Long> {
    Optional<Score> findByUidAndAndClassId(String uid, String classId);
}
```
ContextHolder类用于保存缓存信息
```
public class ContextHolder {
    private static ThreadLocal<ContextInfo> threadLocal = new ThreadLocal<>();

    /**
     * 获取线程上下文
     * @return
     */
    public static ThreadLocal<ContextInfo> getThreadLocal() {
        return threadLocal;
    }

    /**
     * 设置操作上下文
     * @param contextInfo
     */
    public static void setThreadLocal(ContextInfo contextInfo) {
        threadLocal.set(contextInfo);
    }

    /**
     * 清理线程上下文
     */
    public static void clear(){
        try{
            if(null!=threadLocal.get()){
                threadLocal.remove();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadLocal.set(null);
        }
    }

    public static Optional<Score> getScoreVOResult(String uid, String classId){
        ContextInfo context = threadLocal.get();
        if(context == null){
            return null;
        }
        return context.getScoreVOResult().get(Pair.of(uid, classId));
    }

    public static void setScoreVOResult(String uid, String classId, Optional<Score> score){
        ContextInfo context = threadLocal.get();
        if(context == null){
            context = new ContextInfo();
            threadLocal.set(context);
        }
        context.getScoreVOResult().put(Pair.of(uid, classId), score);
    }
}
```

查询时如果缓存中有值直接取出，如果没有先从DB查询再放入缓存
 ```
@Service
public class ThreadLocalCacheService {

    @Autowired
    private ScoreRepo scoreRepo;

    public Optional<Score> queryScore(String uid, String classId) {
        Optional<Score> score = ContextHolder.getScoreVOResult(uid, classId);
        if (null != score) {
            return score;
        }
        score = scoreRepo.findByUidAndAndClassId(uid, classId);
        if (null != score && score.isPresent()) {
            ContextHolder.setScoreVOResult(uid, classId, score);
        }
        return score;
    }
}
```

这里为了模拟重复查询，使用for循环对DB重复查询10次
   ```
@Override
    public ScoreVO queryScoreOne(String uid, String classId) {
        Optional<Score> score = cacheService.queryScore(uid, classId);
        // 查询10次socre
        for (int i = 0; i < 10; i++) {
            score = cacheService.queryScore(uid, classId);
        }
        if (score.isPresent()) {
            return ScoreVO.builder().uid(score.get().getUid()).className(score.get().getClassName()).score(score.get().getScore()).build();
        } else {
            return null;
        }
    }
```
## 效果测试
1. 查询结果测试
 ```
@Test
    public void testQueryScore(){
        String uid = "12345";
        String classId = "1";
        Optional<Score> score1 = cacheService.queryScore(uid, classId);
        Optional<Score> score2 = cacheService.queryScore(uid, classId);
        Assert.assertEquals(score1, score2);
    }
```
只出现一条查询语句，并且对象相等
![查询测试](https://upload-images.jianshu.io/upload_images/7632302-5d7afac594c9072b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2. 使用PostMan测试第一条查询，只出现一次DB查询
http://localhost:8082/score/query1/12345/1
postman测试查询1
![postman测试查询1](https://upload-images.jianshu.io/upload_images/7632302-21e26be51b58f8e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
查询10次，后续都是从缓存中获取
![查询10次，后续都是从缓存中获取](https://upload-images.jianshu.io/upload_images/7632302-6fa6a9f108274e6b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
DB查询结果
![DB查询结果](https://upload-images.jianshu.io/upload_images/7632302-a3e44fad7bdfff42.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
再次发起一次查询，又出现一条查询记录，说明ThreadLocal只缓存当前线程的请求，对新的请求失效，可以采用其他策略。
二次查询结果：
![二次查询结果](https://upload-images.jianshu.io/upload_images/7632302-7ecff0cdebe1d1f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 源码分析
[源码参考](https://github.com/guangxush/ThreadLocalCache)
