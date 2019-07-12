package com.cw.web.front.controller.module;

import com.cw.biz.home.app.dto.AppModuleDto;
import com.cw.biz.home.app.service.ModuleAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 服务开关
 * Created by Administrator on 2017/7/28.
 */
@RestController
public class ModuleController extends AbstractFrontController {
    @Autowired
    private ModuleAppService moduleAppService;

    /**
     * 查询app对应的模块接口
     * 现金贷或则商品贷开关接口
     * @return
     */
    @GetMapping("/sys/getAppModule.json")
    @ResponseBody
    public CPViewResultInfo getAppModule(String appName)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        AppModuleDto appModuleDto = moduleAppService.getAppModule(appName);
        if(!Objects.isNull(appModuleDto)) {
            cpViewResultInfo.setData(appModuleDto.getModuleIds());
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
