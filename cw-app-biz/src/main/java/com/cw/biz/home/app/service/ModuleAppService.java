package com.cw.biz.home.app.service;

import com.cw.biz.home.app.dto.AppInfoDto;
import com.cw.biz.home.app.dto.AppModuleDto;
import com.cw.biz.home.domain.service.ModuleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 后台模块开关服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ModuleAppService {

    @Autowired
    private ModuleDomainService moduleDomainService;

    /**
     * 查询app查询显示模块
     * @return
     */
    public AppModuleDto getAppModule(String appName)
    {
        return moduleDomainService.getAppModule(appName);
    }

    /**
     * app短信配置
     * @param appName
     * @return
     */
    public AppInfoDto getAppSendMessage(String appName)
    {
        return moduleDomainService.getAppSendMessage(appName);
    }
}
