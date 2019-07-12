package com.cw.biz.log.domain.repository;


import com.cw.biz.log.domain.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 日志
 * Created by dujy on 2017-05-20.
 */
public interface LogRepository extends JpaRepository<Log,Long> {

}
