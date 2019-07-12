package com.cw.web.front.controller.product;

import com.cw.biz.product.app.dto.ProductSubTypeDto;
import com.cw.biz.product.app.service.ProductSubTypeAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 产品分类
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ProductSubTypeController extends AbstractFrontController {

    @Autowired
    private ProductSubTypeAppService productAppService;

    /**
     * 查询产品分类
     * @return
     */
    @GetMapping("/product/findProductType.json")
    public CPViewResultInfo findProductType(String appName)
    {
        if(appName==null)
        {
            appName="DSQB";
        }
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductSubTypeDto> productDtos = productAppService.findAll(appName);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }



}
