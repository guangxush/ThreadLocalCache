package com.shgx.threadcache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @auther guangxush
 * @create 2019/8/31
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_score")
public class Score {
    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @Column(name = "uid")
    private String uid;

    /**
     * 课程id
     */
    @Column(name = "class_id")
    private String classId;

    /**
     * 课程名称
     */
    @Column(name = "class_name")
    private String className;

    /**
     * 成绩
     */
    @Column(name = "score")
    private Double score;

    /**
     * 登记时间
     */
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
