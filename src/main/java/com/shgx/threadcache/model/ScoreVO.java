package com.shgx.threadcache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreVO {
    /**
     * 学号
     */
    private String uid;
    /**
     * 课程名称
     */
    private String className;

    /**
     * 成绩
     */
    private Double score;
}
