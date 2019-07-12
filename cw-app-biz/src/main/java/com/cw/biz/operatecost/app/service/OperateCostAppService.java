package com.cw.biz.operatecost.app.service;

import com.cw.biz.channel.app.service.AppMarketAppService;
import com.cw.biz.common.dto.Pages;
import com.cw.biz.home.app.service.AppInfoAppService;
import com.cw.biz.operatecost.app.dto.OperateCostDto;
import com.cw.biz.operatecost.domain.dao.OperateCostDao;
import com.cw.biz.operatecost.domain.entity.OperateCost;
import com.cw.biz.operatecost.domain.service.OperateCostDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 运营成本
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class OperateCostAppService {

    @Autowired
    private OperateCostDomainService operateCostDomainService;
    @Autowired
    private AppInfoAppService appInfoAppService;
    @Autowired
    private AppMarketAppService appMarketAppService;

    @Autowired
    private OperateCostDao dao;
    /**
     * 新增消耗运营成本
     * @param operateCostDto
     * @return
     */
    public Long create(OperateCostDto operateCostDto)
    {
        return operateCostDomainService.create(operateCostDto).getId();
    }

    /**
     * 修改运营成本
     * @param operateCostDto
     * @return
     */
    public Long update(OperateCostDto operateCostDto)
    {
        return operateCostDomainService.update(operateCostDto).getId();
    }

    /**
     * 查询运营成本详情
     * @param id
     * @return
     */
    public OperateCostDto findById(Long id)
    {
        OperateCostDto operateCostDto = new OperateCostDto();
        OperateCost operateCost = operateCostDomainService.findById(id);
        if(operateCost!=null){
            operateCostDto = operateCost.to(OperateCostDto.class);
        }
        return operateCostDto;
    }

    /**
     * 按条件查询运营成本消耗
     * @param dto
     * @return
     */
    public Page<OperateCostDto> findByCondition(OperateCostDto dto)
    {
        Page<OperateCostDto> operateCostDtos = Pages.map(operateCostDomainService.findByCondition(dto),OperateCostDto.class);
        for(OperateCostDto operateCostDto:operateCostDtos.getContent())
        {
            operateCostDto.setAppName(appInfoAppService.findByCode(operateCostDto.getAppName()).getName());
            operateCostDto.setChannel(appMarketAppService.findByChannelNo(operateCostDto.getChannel()).getName());
        }
        return operateCostDtos;
    }

    /**
     * 查询合计值
     * @param operateCostDto
     * @return
     */
    public OperateCostDto getTotalData(OperateCostDto operateCostDto){
        return dao.findCountCost(operateCostDto);
    }
}
