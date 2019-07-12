package com.cw.web.front.controller.cms;

import com.cw.biz.cms.app.dto.CmsDto;
import com.cw.biz.cms.app.service.CmsAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 贷款攻略文章查询
 * Created by Administrator on 2017/7/10.
 */
@RestController
public class CmsController{

    @Autowired
    private CmsAppService cmsAppService;

    /**
     * 查询贷款攻略文章
     * @param cmsDto
     * @return
     */
    @GetMapping("/common/cms/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(CmsDto cmsDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<CmsDto> productDtos = cmsAppService.findByCondition(cmsDto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询贷款攻略文章
     * @return
     */
    @GetMapping("/common/cms/findTitle.json")
    @ResponseBody
    public CPViewResultInfo findTitle()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<CmsDto> cmsDtoList = cmsAppService.findCmsByTitle();
        cpViewResultInfo.setData(cmsDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询文章详情页
     * @param id
     * @return
     */
    @GetMapping("/common/cms/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        CmsDto cmsDtoList = cmsAppService.findById(id);
        cpViewResultInfo.setData(cmsDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
