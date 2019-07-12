package com.cw.web.front.controller.creditcard;

import com.cw.biz.creditcard.app.dto.BankDto;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.creditcard.app.service.BankAppService;
import com.cw.biz.creditcard.app.service.CreditCardAppService;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 信用卡服务
 * Created by dujy on 2017-05-20.
 */
@RestController
public class CreditCardController extends AbstractFrontController {

    @Autowired
    private CreditCardAppService creditCardAppService;

    @Autowired
    private BankAppService bankAppService;
    /**
     * 查询信用卡服务详情
     * @param id
     * @return
     */
    @GetMapping("/creditCard/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CreditCardDto creditCardDto = creditCardAppService.findById(id);
        cpViewResultInfo.setData(creditCardDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有银行卡服务
     * @param creditCardDto
     * @return
     */
    @PostMapping("/creditCard/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody CreditCardDto creditCardDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<CreditCardDto> creditCardDtos = creditCardAppService.findByCondition(creditCardDto);
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询推荐位
     * @param creditCardDto
     * @return
     */
    @PostMapping("/creditCard/findRecommend.json")
    @ResponseBody
    public CPViewResultInfo findCreditCardRecomm(@RequestBody CreditCardDto creditCardDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<CreditCardDto> creditCardDtos = creditCardAppService.findByRecommend();
        cpViewResultInfo.setData(creditCardDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 立即申请
     * @param logDto
     * @return
     */
    @PostMapping("/creditCard/applyCreditCard.json")
    @ResponseBody
    public CPViewResultInfo applyCreditCard(@RequestBody LogDto logDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        creditCardAppService.applyCreditCard(logDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }



    /**
     * 根据参数查询银行信息
     * @param flag
     * @return
     */
    @GetMapping("/creditCard/findBankByPosition.json")
    @ResponseBody
    public CPViewResultInfo findBankByPosition(String flag) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<BankDto> bankDtoList = bankAppService.findBankByPosition(flag);
        cpViewResultInfo.setData(bankDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
