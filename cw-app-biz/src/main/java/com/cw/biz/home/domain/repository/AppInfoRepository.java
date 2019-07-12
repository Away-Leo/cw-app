package com.cw.biz.home.domain.repository;


import com.cw.biz.home.domain.entity.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * APP信息列表
 * Created by dujy on 2017-05-20.
 */
public interface AppInfoRepository extends JpaRepository<AppInfo,Long> {

    AppInfo findByAndCode(String code);
}
