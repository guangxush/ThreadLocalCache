package com.shgx.threadcache.service;

import com.shgx.threadcache.model.ContextHolder;
import com.shgx.threadcache.model.Score;
import com.shgx.threadcache.repository.ScoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
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
