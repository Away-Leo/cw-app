package com.cw.web.backend.controller.product;

import com.cw.biz.home.app.service.HomeAppService;
import com.cw.biz.log.app.dto.ApplyLogDto;
import com.cw.biz.log.app.service.LogAppService;
import com.cw.biz.product.app.dto.*;
import com.cw.biz.product.app.service.ProductAppService;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.core.common.annotation.ActionControllerLog;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品控制器
 * Created by dujy on 2017-05-20.
 */
@RestController
public class ProductManagerController extends AbstractBackendController {

    @Autowired
    private ProductAppService productAppService;
    @Autowired
    private LogAppService logAppService;
    @Autowired
    private HomeAppService homeAppService;
    /**
     * 发布产品
     * @param productDto
     * @return
     */
    @PostMapping("/product/create.json")
    @ResponseBody
    @ActionControllerLog(title = "发布产品",action = "createProduct",isSaveRequestData=true,channel = "web")
    public CPViewResultInfo createProduct(@RequestBody ProductDto productDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productDto.setApplyCondition(productDto.getApplyCondition().replaceAll("huanhang","chr(10)"));
        productDto.setApplyMaterial(productDto.getApplyMaterial().replaceAll("huanhang","chr(10)"));
        Long productId = productAppService.create(productDto);
        cpViewResultInfo.setData(productId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 修改产品
     * @param productDto
     * @return
     */
    @PostMapping("/product/update.json")
    @ResponseBody
    @ActionControllerLog(title = "修改产品",action = "updateProduct",isSaveRequestData=true,channel = "web")
    public CPViewResultInfo updateProduct(@RequestBody ProductDto productDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long productId = productAppService.update(productDto);
        cpViewResultInfo.setData(productId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("产品修改成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用产品
     * @param productVersionDto
     * @return
     */
    @PostMapping("/product/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody ProductVersionDto productVersionDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.enable(productVersionDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("产品停用成功");
        return cpViewResultInfo;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    @GetMapping("/product/findById.json")
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        ProductDto productDto = productAppService.findProductById(id);
        cpViewResultInfo.setData(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("产品详情查询成功");
        return cpViewResultInfo;
    }

    /**
     * 搜索产品
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody ProductSearchDto productSearchDto)
    {
        productSearchDto.setSizePerPage(100);
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productSearchDto.setSysType(1);//后台查询所有产品
        Page<ProductListDto> productDtos = productAppService.findByCondition(productSearchDto);
        //构造产品申请用户数
        List<ApplyLogDto> applyLogDtoList = logAppService.findUserByProductId();
        for(ProductListDto productListDto : productDtos)
        {
            for(ApplyLogDto applyLogDto:applyLogDtoList)
            {
                if(applyLogDto.getProductId().intValue()==productListDto.getId().intValue()){
                    productListDto.setApplyNum(applyLogDto.getApplyNum());
                    productListDto.setClickNum(applyLogDto.getApplyTime());
                    break;
                }
            }
        }
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


    /**
     * 产品统计数据
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/productCount.json")
    public CPViewResultInfo productCount(@RequestBody ProductSearchDto productSearchDto)
    {
        productSearchDto.setSizePerPage(100);
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productSearchDto.setSysType(1);//后台查询所有产品
        List<ProductListDto> productDtos = homeAppService.productCount(productSearchDto);
        //构造产品申请用户数
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有产品数据
     * @return
     */
    @GetMapping("/product/findAll.json")
    @ResponseBody
    public CPViewResultInfo findAll(ProductSearchDto searchDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductDto> productDtos = productAppService.findAll(searchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 修改产品排序
     * @return
     */
    @PostMapping("/product/saveProductSort.json")
    public CPViewResultInfo saveProductSort(@RequestBody ProductSortDto productSortDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.saveProductSort(productSortDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 产品流量配置控制
     * @return
     */
    @PostMapping("/product/saveProductConfig.json")
    public CPViewResultInfo saveProductConfig(@RequestBody ProductSortDto productSortDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.saveProductConfig(productSortDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 产品排序列表
     * @param productSearchDto
     * @return
     */
    @PostMapping("/product/findByProductSortList.json")
    @ResponseBody
    public CPViewResultInfo findByProductSortList(@RequestBody ProductSearchDto productSearchDto){
        productSearchDto.setSizePerPage(100);
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productSearchDto.setSysType(1);//后台查询所有产品
        List<ProductListDto> productDtos = productAppService.findByProductSortList(productSearchDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 产品申请后推荐产品
     * @return
     */
    @GetMapping("/product/productRecommend.json")
    public CPViewResultInfo findApplyProductRecommend(Long productId,String versionNo){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductRecommendListDto> productRecommendListDtos = productAppService.findRecommendProductByProductId(productId,versionNo,"backend");
        cpViewResultInfo.setData(productRecommendListDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询未推荐的产品
     * @param searchDto
     * @return
     */
    @PostMapping("/product/productNoRecommend.json")
    @ResponseBody
    public CPViewResultInfo productNoRecommend(@RequestBody MarketDailyDto searchDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ProductRecommendListDto> productRecommendListDtos = productAppService.findNoRecommendByProductId(searchDto.getProductId(),searchDto.getProductName());
        cpViewResultInfo.setData(productRecommendListDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 设置产品推荐
     * @param recommendListDto
     * @return
     */
    @PostMapping("/product/productSetRecommend.json")
    @ResponseBody
    public CPViewResultInfo productSetRecommend(@RequestBody ProductRecommendListDto recommendListDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.productSetRecommend(recommendListDto);
        cpViewResultInfo.setData(null);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }

    /**
     * 修改产品推荐功能开关
     * @param productDto
     * @return
     */
    @PostMapping("/product/updateProductRecommendFlag.json")
    @ResponseBody
    public CPViewResultInfo updateProductRecommendFlag(@RequestBody ProductDto productDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        productAppService.updateProductRecommendFlag(productDto);
        cpViewResultInfo.setData(null);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("操作成功");
        return cpViewResultInfo;
    }
}
