package com.cw.core.common.codecategory.domain.service;

import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.codecategory.app.dto.CodeCategoryDto;
import com.cw.core.common.codecategory.domain.entity.CodeCategory;
import com.cw.core.common.codecategory.domain.repository.CodeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: CodeCategoryDomainService.java
 * @Description: 下拉大类服务
 * @Author: Away
 * @Date: 2018/4/23 18:02
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Service
public class CodeCategoryDomainService extends BaseDomainService<CodeCategoryRepository,CodeCategory,CodeCategoryDto> {

    private final CodeCategoryRepository codeCategoryRepository;

    @Autowired
    public CodeCategoryDomainService(CodeCategoryRepository codeCategoryRepository){
        this.codeCategoryRepository=codeCategoryRepository;
    }

}
