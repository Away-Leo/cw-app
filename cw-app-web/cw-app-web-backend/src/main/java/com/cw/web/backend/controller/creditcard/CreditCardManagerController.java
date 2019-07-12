package com.cw.web.backend.controller.creditcard;

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
 * 信用卡服务
 * Created by dujy on 2017-06-26.
 */
@RestController
public class CreditCardManagerController extends AbstractBackendController {

    @Autowired
    private CreditCardAppService creditCardAppService;

    /**
     * 查询信用卡服务详情
     * @param creditCardDto
     * @return
     */
    @PostMapping("/credit/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody CreditCardDto creditCardDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long cardId = creditCardAppService.create(creditCardDto);
        cpViewResultInfo.setData(cardId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("新增成功");
        return cpViewResultInfo;
    }

    /**
     * 查询信用卡详情
     * @param id
     * @return
     */
    @GetMapping("/credit/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CreditCardDto creditCardDto = creditCardAppService.findById(id);
        cpViewResultInfo.setData(creditCardDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有银行卡服务
     * @param creditCardDto
     * @return
     */
    @PostMapping("/credit/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody CreditCardDto creditCardDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<CreditCardDto> creditCardDtos = creditCardAppService.findByCondition(creditCardDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用产品
     * @param creditCardDto
     * @return
     */
    @PostMapping("/credit/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody CreditCardDto creditCardDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        creditCardAppService.enable(creditCardDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("停用成功");
        return cpViewResultInfo;
    }
    /**
     * 修改信用卡信息
     * @param creditCardDto
     * @return
     */
    @PostMapping("/credit/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody CreditCardDto creditCardDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = creditCardAppService.update(creditCardDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 信用卡申请统计
     * @param reportSearchDto
     * @return
     */
    @PostMapping("/credit/applyTotal.json")
    @ResponseBody
    public CPViewResultInfo applyTotal(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<CreditCardDto> creditCardDtoList = creditCardAppService.creditCardApply(reportSearchDto);
        cpViewResultInfo.setData(creditCardDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
