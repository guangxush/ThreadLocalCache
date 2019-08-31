package com.shgx.threadcache.service;

import com.shgx.threadcache.model.Score;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadLocalCacheTest {

    @Autowired
    private ThreadLocalCacheService cacheService;

    @Test
    public void testQueryScore(){
        String uid = "12345";
        String classId = "1";
        Optional<Score> score1 = cacheService.queryScore(uid, classId);
        Optional<Score> score2 = cacheService.queryScore(uid, classId);
        Assert.assertEquals(score1, score2);
    }
}
