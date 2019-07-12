package com.cw.biz.cms.app.service;

import com.cw.biz.cms.app.dto.CmsDto;
import com.cw.biz.cms.domain.entity.Cms;
import com.cw.biz.cms.domain.service.CmsDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 贷款攻略文章
 * Created by Administrator on 2017/7/10.
 */
@Transactional
@Service
public class CmsAppService {

    @Autowired
    private CmsDomainService cmsDomainService;

    /**
     * 新增贷款攻略
     * @param cmsDto
     * @return
     */
    public Long create(CmsDto cmsDto)
    {
        return cmsDomainService.create(cmsDto).getId();
    }

    /**
     * 新增贷款攻略
     * @param cmsDto
     * @return
     */
    public Long update(CmsDto cmsDto)
    {
        return cmsDomainService.update(cmsDto).getId();
    }

    /**
     * 停用启用贷款攻略
     * @param cmsDto
     * @return
     */
    public void enable(CmsDto cmsDto)
    {
        cmsDomainService.enable(cmsDto);
    }

    /**
     * 查询贷款攻略详情
     * @param id
     * @return
     */
    public CmsDto findById(Long id)
    {
        return cmsDomainService.findById(id).to(CmsDto.class);
    }

    /**
     * 按条件搜索贷款攻略
     * @param dto
     * @return
     */
    public Page<CmsDto> findByCondition(CmsDto dto)
    {
        return Pages.map(cmsDomainService.findByCondition(dto),CmsDto.class);
    }

    /**
     * 搜索攻略标题
     */

    public List<CmsDto> findCmsByTitle()
    {
        List<Cms> cmsList = cmsDomainService.findAll();
        List<CmsDto> cmsDtoList = new ArrayList<>();
        for(Cms cms:cmsList)
        {
            CmsDto cmsDto = cms.to(CmsDto.class);
            cmsDtoList.add(cmsDto);
        }
        return cmsDtoList;
    }

}
