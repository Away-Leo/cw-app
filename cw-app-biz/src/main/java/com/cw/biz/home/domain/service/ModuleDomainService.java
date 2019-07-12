package com.cw.biz.home.domain.service;


import com.cw.biz.home.app.dto.AppInfoDto;
import com.cw.biz.home.app.dto.AppModuleDto;
import com.cw.biz.home.app.dto.HomeDto;
import com.cw.biz.home.domain.dao.ModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ModuleDomainService {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 统计新增用户数
     * @return
     */
    public AppModuleDto getAppModule(String appName)
    {
        AppModuleDto appModule = moduleDao.getAppModule(appName);
        return appModule;
    }


    public AppInfoDto getAppSendMessage(String appName)
    {
        AppInfoDto appModule = moduleDao.getAppSendMessage(appName);
        return appModule;
    }

}
