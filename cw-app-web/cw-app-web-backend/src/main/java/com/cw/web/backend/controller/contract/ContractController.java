package com.cw.web.backend.controller.contract;

import com.cw.biz.contract.app.dto.ContractDto;
import com.cw.biz.contract.app.service.ContractAppService;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.creditcard.app.service.CreditCardAppService;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合同控制器
 * Created by dujy on 2017-11-1.
 */
@RestController
public class ContractController extends AbstractBackendController {

    @Autowired
    private ContractAppService contractAppService;

    /**
     * 保存合同服务详情
     * @param contractDto
     * @return
     */
    @PostMapping("/contract/update.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody ContractDto contractDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = contractAppService.update(contractDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查询成功
     * @param reportSearchDto
     * @return
     */
    @PostMapping("/contract/findAll.json")
    @ResponseBody
    public CPViewResultInfo findAll(@RequestBody ReportSearchDto reportSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ContractDto> contractDtos = contractAppService.findAll(reportSearchDto);
        cpViewResultInfo.setData(contractDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
