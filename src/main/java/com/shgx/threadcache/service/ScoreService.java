package com.shgx.threadcache.service;

import com.shgx.threadcache.model.ScoreVO;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
public interface ScoreService {

    /**
     * 查询成绩1
     * @param uid
     * @param classId
     * @return
     */
    ScoreVO queryScoreOne(String uid, String classId);

    /**
     * 查询成绩2
     * @param uid
     * @param classId
     * @return
     */
    ScoreVO queryScoreTwo(String uid, String classId);
}
