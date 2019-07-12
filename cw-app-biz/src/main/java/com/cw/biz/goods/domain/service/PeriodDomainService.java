package com.cw.biz.goods.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.goods.app.dto.PeriodDto;
import com.cw.biz.goods.domain.entity.Period;
import com.cw.biz.goods.domain.repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class PeriodDomainService {

    @Autowired
    private PeriodRepository repository;
    /**
     * 新增商品
     * @param periodDto
     * @return
     */
    public Period create(PeriodDto periodDto){
        Period period = new Period();
        period.from(periodDto);
        return repository.save(period);
    }

    /**
     * 修改商品
     * @param periodDto
     * @return
     */
    public Period update(PeriodDto periodDto){
        Period period = repository.findOne(periodDto.getId());
        if(period == null){
            CwException.throwIt("商品不存在");
        }
        period.from(periodDto);
        repository.save(period);

        return period;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Period findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 按产品查询分期
     * @return
     */
    public List<Period> findAll(Long goodsId){
        return repository.findByGoodsId(goodsId);
    }

}
