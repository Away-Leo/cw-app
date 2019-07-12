package com.cw.biz.goods.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.goods.app.dto.GoodsDto;
import com.cw.biz.goods.domain.entity.Goods;
import com.cw.biz.goods.domain.service.GoodsDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 商品服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class GoodsAppService {

    @Autowired
    private GoodsDomainService goodsDomainService;

    /**
     * 新增商品
     * @param goodsDto
     * @return
     */
    public Long create(GoodsDto goodsDto){
        return goodsDomainService.create(goodsDto).getId();
    }

    /**
     * 修改商品
     * @param goodsDto
     * @return
     */
    public Long update(GoodsDto goodsDto)
    {
        return goodsDomainService.update(goodsDto).getId();
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public GoodsDto findById(Long id){
        GoodsDto goodsDto = new GoodsDto();
        Goods goods = goodsDomainService.findById(id);
        if(goods!=null){
            goodsDto = goods.to(GoodsDto.class);
        }
        return goodsDto;
    }

    /**
     * 按条件查询商品
     * @param dto
     * @return
     */
    public Page<GoodsDto> findByCondition(GoodsDto dto){
        return Pages.map(goodsDomainService.findByCondition(dto),GoodsDto.class);
    }

}
