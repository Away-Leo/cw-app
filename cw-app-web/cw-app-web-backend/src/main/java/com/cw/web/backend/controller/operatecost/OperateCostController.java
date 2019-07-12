package com.cw.web.backend.controller.operatecost;

import com.cw.biz.CPContext;
import com.cw.biz.operatecost.app.dto.OperateCostDto;
import com.cw.biz.operatecost.app.service.OperateCostAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 运营成本录入
 * Created by dujy on 2017-06-26.
 */
@RestController
public class OperateCostController extends AbstractBackendController {

    @Autowired
    private OperateCostAppService operateCostAppService;

    /**
     * 新增运营成本
     * @param operateCostDto
     * @return
     */
    @PostMapping("/cost/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody OperateCostDto operateCostDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        operateCostDto.setOperateNo(CPContext.getContext().getSeUserInfo().getId());
        Long costId = operateCostAppService.create(operateCostDto);
        cpViewResultInfo.setData(costId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存");
        return cpViewResultInfo;
    }

    /**
     * 编辑运营成本
     * @param operateCostDto
     * @return
     */
    @PostMapping("/cost/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody OperateCostDto operateCostDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = operateCostAppService.update(operateCostDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }
    /**
     * 查询banner详情
     * @param id
     * @return
     */
    @GetMapping("/cost/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        OperateCostDto operateCostDto = operateCostAppService.findById(id);
        cpViewResultInfo.setData(operateCostDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询运营成本
     * @param operateCostDto
     * @return
     */
    @PostMapping("/cost/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody OperateCostDto operateCostDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<OperateCostDto> creditCardDtos = operateCostAppService.findByCondition(operateCostDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 查询运营成本
     * @param operateCostDto
     * @return
     */
    @PostMapping("/cost/getTotal.json")
    @ResponseBody
    public CPViewResultInfo getTotal(@RequestBody OperateCostDto operateCostDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        OperateCostDto creditCardDtos = operateCostAppService.getTotalData(operateCostDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


}
