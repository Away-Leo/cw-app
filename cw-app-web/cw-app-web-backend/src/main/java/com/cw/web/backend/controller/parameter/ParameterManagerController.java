package com.cw.web.backend.controller.parameter;

import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.SpinnerParameterDto;
import com.cw.biz.parameter.app.service.ParameterAppService;
import com.cw.biz.parameter.app.service.SpinnerParameterAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 参数配置
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ParameterManagerController extends AbstractBackendController {

    @Autowired
    private ParameterAppService parameterAppService;

    @Autowired
    private SpinnerParameterAppService spinnerParameterAppService;
    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    @PostMapping("/parameter/update.json")
    @ResponseBody
    public CPViewResultInfo updateParameter(@RequestBody IndexParameterDto parameterDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long parameterId = parameterAppService.update(parameterDto);
        cpViewResultInfo.setData(parameterId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("参数保存成功");
        return cpViewResultInfo;
    }

    @PostMapping("/parameter/findSpinnerByType.json")
    @ResponseBody
    public CPViewResultInfo updateParameter(@RequestBody SpinnerParameterDto parameterDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<SpinnerParameterDto> parameterList = spinnerParameterAppService.findByType(parameterDto.getCode());
        cpViewResultInfo.setData(parameterList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }
}
