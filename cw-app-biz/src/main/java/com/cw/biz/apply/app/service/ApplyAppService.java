package com.cw.biz.apply.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.apply.app.dto.ApplyDto;
import com.cw.biz.apply.domain.service.ApplyDomainService;
import com.cw.biz.common.dto.Pages;
import com.cw.biz.product.app.dto.ProductDto;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 借款申请服务
 * Created by Administrator on 2017/6/13.
 */
@Transactional
@Service
public class ApplyAppService {

    @Autowired
    private ApplyDomainService domainService;
    /**
     * 新增借款申请
     * @param applyDto
     * @return
     */
    public Long create(ApplyDto applyDto)
    {
        return domainService.create(applyDto).getId();
    }

    /**
     * 按条件搜索申请信息
     * @return
     */
    public Page<ApplyDto> findByCondition(ApplyDto applyDto)
    {
        applyDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        return Pages.map(domainService.findByCondition(applyDto),ApplyDto.class);
    }

}
