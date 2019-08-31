package com.shgx.threadcache.model;

import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
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
