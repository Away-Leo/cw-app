package com.cw.biz.goods.domain.repository;


import com.cw.biz.goods.domain.entity.PostAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 邮寄地址
 * Created by dujy on 2017-05-20.
 */
public interface PostAddressRepository extends JpaRepository<PostAddress,Long> {

    List<PostAddress> findByUserId(Long userId);
}
