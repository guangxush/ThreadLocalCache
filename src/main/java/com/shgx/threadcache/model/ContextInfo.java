package com.shgx.threadcache.model;


import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;


/**
 * 上下文信息
 * @author: guangxush
 * @create: 2019/08/31
 */
public class ContextInfo {

    private Map<Pair<String, String>, Optional<Score>>  scoreVOResult = Maps.newHashMap();

    public Map<Pair<String, String>, Optional<Score>> getScoreVOResult() {
        return scoreVOResult;
    }

    public void setScoreVOResult(Map<Pair<String, String>, Optional<Score>> scoreVOResult) {
        this.scoreVOResult = scoreVOResult;
    }
}
