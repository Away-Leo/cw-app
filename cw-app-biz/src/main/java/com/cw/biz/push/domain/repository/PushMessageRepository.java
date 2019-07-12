package com.cw.biz.push.domain.repository;

import com.cw.biz.push.domain.entity.PushMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 推送消息
 * Created by dujy on 2017-05-20.
 */
public interface PushMessageRepository extends JpaRepository<PushMessage,Long>,JpaSpecificationExecutor<PushMessage> {

    PushMessage findByContent(String title);
}
