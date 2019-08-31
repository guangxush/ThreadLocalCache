package com.shgx.threadcache.service.impl;

import com.shgx.threadcache.exception.DemoInternalError;
import com.shgx.threadcache.model.Score;
import com.shgx.threadcache.model.ScoreVO;
import com.shgx.threadcache.repository.ScoreRepo;
import com.shgx.threadcache.service.ScoreService;
import com.shgx.threadcache.service.ThreadLocalCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ThreadLocalCacheService cacheService;

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

    @Override
    public ScoreVO queryScoreTwo(String uid, String classId) {
        Optional<Score> score = cacheService.queryScore(uid, classId);
        if (score.isPresent()) {
            return ScoreVO.builder().uid(score.get().getUid()).className(score.get().getClassName()).score(score.get().getScore()).build();
        } else {
            return null;
        }
    }
}