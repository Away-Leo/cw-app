package com.cw.web.front.controller.product;

import com.cw.biz.apply.app.dto.ApplyDto;
import com.cw.biz.apply.app.dto.ApplyMessageDto;
import com.cw.biz.apply.app.dto.ApplyQuestionnaireDto;
import com.cw.biz.apply.app.service.ApplyAppService;
import com.cw.biz.apply.app.service.ApplyMessageAppService;
import com.cw.biz.apply.app.service.ApplyQuestionnaireAppService;
import com.cw.biz.apply.domain.entity.ApplyQuestionnaire;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.domain.entity.Log;
import com.cw.biz.product.app.dto.*;
import com.cw.biz.product.app.service.ProductAppService;
import com.cw.biz.product.domain.entity.ProductStore;
import com.cw.core.common.util.AppUtils;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品控制器
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ProductController extends AbstractFrontController {

    @Autowired
    private ProductAppService productAppService;
    @Autowired
    private ApplyAppService applyAppService;
    @Autowired
    private ApplyQuestionnaireAppService applyQuestionnaireAppService;
    @Autowired
    private ApplyMessageAppService applyMessageAppService;
    /**
     * 查询产品详情
     * @param id
     * @return
     */
    @GetMapping("/product/findById.json")
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductDto productDto = productAppService.findById(id);
        cpViewResultInfo.setData(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 搜索产品
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/findByCondition.json")
    public CPViewResultInfo findByCondition(@RequestBody ProductSearchDto productSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<ProductListDto> productDtos = productAppService.findByCondition(productSearchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     *  按条件搜索
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/findByConditionVersion.json")
    public CPViewResultInfo findByConditionVersion(@RequestBody ProductSearchDto productSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<ProductListDto> productDtos = productAppService.findByConditionVersion(productSearchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有产品数据
     * @return
     */
    @PostMapping("/product/findAll.json")
    public CPViewResultInfo findAll(@RequestBody ProductSearchDto searchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductDto> productDtos = productAppService.findAll(searchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询产品数据是否有更新
     * @return
     */
    @GetMapping("/product/findProductVersion.json")
    public CPViewResultInfo findProductVersion(String channelNo)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductVersionDto productVersionDto = productAppService.findProductVersion(channelNo);
        cpViewResultInfo.setData(productVersionDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 立即申请
     * @param logDto
     * @return
     */
    @PostMapping("/product/applyLoan.json")
    public CPViewResultInfo applyLoan(@RequestBody LogDto logDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.applyLoan(logDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询我的产品借款申请列表V1.1
     * @return
     */
    @GetMapping("/product/findApplyList.json")
    public CPViewResultInfo findApplyList(ApplyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<ApplyDto> applyDtos = applyAppService.findByCondition(dto);
        cpViewResultInfo.setData(applyDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询产品门店
     * @return
     */
    @GetMapping("/product/findProductStore.json")
    public CPViewResultInfo findProductStore(String name,String appName)
    {
        if(appName==null)
        {
            appName= AppUtils.appNameMBD;
        }
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductStoreDto> productStoreDtos = productAppService.findStoreByName(name,appName);
        cpViewResultInfo.setData(productStoreDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 申请调查
     * @param applyQuestionnaireDto
     * @return
     */
    @PostMapping("/product/applyInvestigation.json")
    public CPViewResultInfo applyInvestigation(@RequestBody ApplyQuestionnaireDto applyQuestionnaireDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        applyQuestionnaireAppService.create(applyQuestionnaireDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查找推荐产品
     * @return
     */
    @GetMapping("/product/findRecommendProduct.json")
    public CPViewResultInfo findRecommendProduct(ProductSearchDto productSearchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductListDto> productStoreDtos = productAppService.findRecommendProduct(productSearchDto);
        cpViewResultInfo.setData(productStoreDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


    /**
     * 查询申请结果
     * @return
     */
    @GetMapping("/product/findApplyResult.json")
    public CPViewResultInfo findApplyResult(){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ApplyMessageDto> applyMessageDtos = applyMessageAppService.findAll();
        cpViewResultInfo.setData(applyMessageDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 产品申请后推荐产品
     * @return
     */
    @GetMapping("/product/findApplyProductRecommend.json")
    public CPViewResultInfo findApplyProductRecommend(Long productId,String versionNo,String channelNo){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductRecommendListDto> productListDtoList = productAppService.findRecommendProductByProductId(productId,versionNo,channelNo);
        cpViewResultInfo.setData(productListDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
