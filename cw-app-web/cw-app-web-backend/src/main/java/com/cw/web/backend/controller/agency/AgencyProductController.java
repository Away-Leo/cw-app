package com.cw.web.backend.controller.agency;

import com.cw.biz.agency.app.dto.AgencyProductDto;
import com.cw.biz.agency.domain.entity.AgencyProductInfo;
import com.cw.biz.agency.domain.service.AgencyProductService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 代理产品维护
 */
@RestController
public class AgencyProductController extends AbstractBackendController {

    @Autowired
    private AgencyProductService agencyProductService;

    @PostMapping("/agency/agencyProductCreate.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody AgencyProductDto productDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyProductService.createProduct(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    @GetMapping("/agency/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        AgencyProductInfo info = agencyProductService.findProductById(id);
        cpViewResultInfo.setData(info);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    @PostMapping("/agency/updateById.json")
    @ResponseBody
    public CPViewResultInfo updateById(@RequestBody AgencyProductDto productDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyProductService.updateById(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 查询产品列表
     * @param productDto
     * @return
     */
    @PostMapping("/agency/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody AgencyProductDto productDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<AgencyProductDto> agencyProductDtos = agencyProductService.findByCondition(productDto);
        cpViewResultInfo.setData(agencyProductDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用产品
     * @param productDto
     * @return
     */
    @PostMapping("/agency/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody AgencyProductDto productDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyProductService.enable(productDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
