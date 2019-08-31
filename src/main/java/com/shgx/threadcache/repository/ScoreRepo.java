package com.shgx.threadcache.repository;

import com.shgx.threadcache.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: guangxush
 * @create: 2019/08/31
 */
public interface ScoreRepo extends JpaRepository<Score, Long> {

    Optional<Score> findByUidAndAndClassId(String uid, String classId);
}
