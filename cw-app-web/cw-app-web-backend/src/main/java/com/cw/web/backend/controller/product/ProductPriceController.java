package com.cw.web.backend.controller.product;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.home.app.service.HomeAppService;
import com.cw.biz.product.app.dto.ProductCooperativePriceDto;
import com.cw.biz.product.app.dto.ProductDto;
import com.cw.biz.product.app.service.ProductPriceAppService;
import com.cw.core.common.annotation.ActionControllerLog;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品价格管理
 * Created by Administrator on 2017/9/27.
 */
@RestController
public class ProductPriceController extends AbstractBackendController {

    @Autowired
    private ProductPriceAppService productPriceAppService;

    /**
     * 查询产品价格列表
     * @param dto
     * @return
     */
    @PostMapping("/product/getProductPrice.json")
    @ResponseBody
    public CPViewResultInfo getProductPrice(@RequestBody ProductCooperativePriceDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductCooperativePriceDto> productCooperativePriceDtoList = productPriceAppService.findAll(dto.getProductId());
        cpViewResultInfo.setData(productCooperativePriceDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 查询价格
     * @param dto
     * @return
     */
    @GetMapping("/product/productPriceById.json")
    @ResponseBody
    public CPViewResultInfo productPriceById(ProductCooperativePriceDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductCooperativePriceDto productCooperativePriceDto = productPriceAppService.findById(dto.getId());
        cpViewResultInfo.setData(productCooperativePriceDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 修改价格
     * @param dto
     * @return
     */
    @PostMapping("/product/updateProductPrice.json")
    @ResponseBody
    public CPViewResultInfo updateProductPrice(@RequestBody ProductCooperativePriceDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = productPriceAppService.update(dto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 保存产品价格
     * @param dto
     * @return
     */
    @PostMapping("/product/saveProductPrice.json")
    @ResponseBody
    public CPViewResultInfo saveProductPrice(@RequestBody ProductCooperativePriceDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long id = productPriceAppService.create(dto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 删除价格
     * @param dto
     * @return
     */
    @GetMapping("/product/deletePrice.json")
    @ResponseBody
    public CPViewResultInfo deletePrice(ProductCooperativePriceDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productPriceAppService.delete(dto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("删除成功");
        return cpViewResultInfo;
    }

}
