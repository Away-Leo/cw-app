package com.cw.core.common.codecategory.domain.service;

import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.codecategory.app.dto.SimpleCodeDto;
import com.cw.core.common.codecategory.domain.entity.SimpleCode;
import com.cw.core.common.codecategory.domain.repository.SimpleCodeRepository;
import com.cw.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: SimpleCodeDomainService.java
 * @Description: 下拉菜单数据字典服务
 * @Author: Away
 * @Date: 2018/4/23 18:05
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Service
public class SimpleCodeDomainService extends BaseDomainService<SimpleCodeRepository,SimpleCode,SimpleCodeDto> {

    private final SimpleCodeRepository simpleCodeRepository;

    @Autowired
    public SimpleCodeDomainService(SimpleCodeRepository simpleCodeRepository) {
        this.simpleCodeRepository = simpleCodeRepository;
    }

    /**
     * @Author: Away
     * @Title: findByCodeCategoryId
     * @Description: 按照大类查找下拉数据
     * @Param: codeCategoryId
     * @Return: java.util.List<com.laman.fusion.base.codecategory.app.dto.SimpleCodeDto>
     * @Date: 2018/4/24 9:50
     * @Version: 2018/4/24 9:50
     */
    public List<SimpleCodeDto> findByCategoryCd(String codeCategoryId) throws BusinessException {
        if(ObjectHelper.isNotEmpty(codeCategoryId)){
            return this.toDtoList(this.simpleCodeRepository.findByCategoryCdAndDeletedFalse(codeCategoryId),SimpleCodeDto.class);
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }

    /**
     * @Author: Away
     * @Title: findByCode
     * @Description: 按照唯一编号查找
     * @Param: code 唯一编号
     * @Return: com.laman.fusion.base.codecategory.app.dto.SimpleCodeDto
     * @Date: 2018/4/24 9:58
     * @Version: 2018/4/24 9:58
     */
    public SimpleCodeDto findByCode(String code) throws BusinessException {
        if(ObjectHelper.isNotEmpty(code)){
            return toDto(this.simpleCodeRepository.findByCodeAndDeletedFalse(code),SimpleCodeDto.class);
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }

}
