package com.cw.biz.creditcard.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.common.dto.Pages;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.creditcard.domain.entity.CreditCard;
import com.cw.biz.creditcard.domain.service.CreditCardDomainService;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class CreditCardAppService {

    @Autowired
    private CreditCardDomainService creditCardDomainService;

    /**
     * 新增产品
     * @param creditCardDto
     * @return
     */
    public Long create(CreditCardDto creditCardDto)
    {
        return creditCardDomainService.create(creditCardDto).getId();
    }

    /**
     * 新增产品
     * @param creditCardDto
     * @return
     */
    public Long update(CreditCardDto creditCardDto)
    {
        return creditCardDomainService.update(creditCardDto).getId();
    }

    /**
     * 停用启用产品
     * @param productDto
     * @return
     */
    public void enable(CreditCardDto productDto)
    {
        creditCardDomainService.enable(productDto);
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public CreditCardDto findById(Long id)
    {
        return creditCardDomainService.findById(id).to(CreditCardDto.class);
    }

    /**
     * 按条件搜索产品列表
     * @param dto
     * @return
     */
    public Page<CreditCardDto> findByCondition(CreditCardDto dto)
    {
        return Pages.map(creditCardDomainService.findByCondition(dto),CreditCardDto.class);
    }

    /**
     * 查询banner位置
     * @return
     */
    public List<CreditCardDto> findByRecommend()
    {
        List<CreditCardDto> creditCardDtoList = new ArrayList<>();
        List<CreditCard> creditCardList = creditCardDomainService.findByRecommend();
        for(CreditCard creditCard:creditCardList)
        {
            creditCardDtoList.add(creditCard.to(CreditCardDto.class));
        }
        return creditCardDtoList;
    }

    /**
     * 信用卡申请
     * @param dto
     */
    public void applyCreditCard(LogDto dto)
    {
        dto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        dto.setType(LogEnum.CREDITCARD_APPLY);
        creditCardDomainService.applyCreditCard(dto);
    }

    /**
     * 信用卡申请统计
     * @param reportSearchDto
     * @return
     */
    public List<CreditCardDto> creditCardApply(ReportSearchDto reportSearchDto){
        return creditCardDomainService.creditCardApply(reportSearchDto);
    }

}
