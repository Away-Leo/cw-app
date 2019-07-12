package com.cw.biz.goods.app.service;

import com.cw.biz.goods.app.dto.PeriodDto;
import com.cw.biz.goods.domain.entity.Period;
import com.cw.biz.goods.domain.service.PeriodDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 分期服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class PeriodAppService {

    @Autowired
    private PeriodDomainService periodDomainService;

    /**
     * 新增分期
     * @param periodDto
     * @return
     */
    public Long create(PeriodDto periodDto){
        return periodDomainService.create(periodDto).getId();
    }

    /**
     * 修改分期
     * @param goodsDto
     * @return
     */
    public Long update(PeriodDto goodsDto)
    {
        return periodDomainService.update(goodsDto).getId();
    }

    /**
     * 查询分期
     * @param id
     * @return
     */
    public PeriodDto findById(Long id){
        PeriodDto periodDto = new PeriodDto();
        Period period = periodDomainService.findById(id);
        if(period!=null){
            periodDto = period.to(PeriodDto.class);
        }
        return periodDto;
    }

    /**
     * 按条件查询分期
     * @param dto
     * @return
     */
    public List<PeriodDto> findByCondition(PeriodDto dto){
        List<PeriodDto> periodDtos = new ArrayList<>();
        List<Period> periodList = periodDomainService.findAll(dto.getGoodsId());
        for(Period period:periodList){
            periodDtos.add(period.to(PeriodDto.class));
        }
        return periodDtos;
    }

}
