package com.cw.web.front.controller.goods;

import com.cw.biz.goods.app.dto.GoodsDto;
import com.cw.biz.goods.app.service.GoodsAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 商品服务
 * Created by dujy on 2017-06-26.
 */
@RestController
public class GoodsController extends AbstractFrontController {

    @Autowired
    private GoodsAppService goodsAppService;

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/goods/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        GoodsDto goodsDto = goodsAppService.findById(id);
        cpViewResultInfo.setData(goodsDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询商品详情成功");
        return cpViewResultInfo;
    }

    /**
     * 查询商品列表
     * @param goodsDto
     * @return
     */
    @PostMapping("/goods/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody GoodsDto goodsDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<GoodsDto> goodsDtoPage = goodsAppService.findByCondition(goodsDto);
        cpViewResultInfo.setData(goodsDtoPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询商品列表成功");
        return cpViewResultInfo;
    }

}
