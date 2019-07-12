package com.cw.biz.log.domain.repository;


import com.cw.biz.log.domain.entity.SendSmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 日志
 * Created by dujy on 2017-05-20.
 */
public interface SendSmsLogRepository extends JpaRepository<SendSmsLog,Long> {

    @Query(value = "select  * from cw_log_send_sms where phone=?1 and date_format(raw_add_time,'%y-%m-%d %H:%i')=date_format(now(),'%y-%m-%d %H:%i') ",nativeQuery = true)
    List<SendSmsLog> findByPhone(String findByPhone);
}
