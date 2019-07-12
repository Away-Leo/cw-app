package com.cw.biz.message.domain.repository;

import com.cw.biz.message.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 系统消息
 * Created by dujy on 2017-05-20.
 */
public interface MessageRepository extends JpaRepository<Message,Long>,JpaSpecificationExecutor<Message> {
}
