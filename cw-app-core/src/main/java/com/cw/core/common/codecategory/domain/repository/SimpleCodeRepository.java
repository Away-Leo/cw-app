package com.cw.core.common.codecategory.domain.repository;


import com.cw.core.common.base.BaseRepository;
import com.cw.core.common.codecategory.domain.entity.SimpleCode;

import java.util.List;

/**
 * @Title: SimpleCodeRepository.java
 * @Description:  下拉菜单数据字典操作库
 * @Author: Away
 * @Date: 2018/4/23 17:55
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public interface SimpleCodeRepository extends BaseRepository<SimpleCode,Long> {

    /**
     * @Author: Away
     * @Title: findByCodeCategoryIdAndDeletedFalse
     * @Description: 按照大类编号查找
     * @Param: codeCategoryId 大类编号ID
     * @Return: java.util.List<com.laman.fusion.base.codecategory.domain.entity.SimpleCode>
     * @Date: 2018/4/23 18:08
     * @Version: 2018/4/23 18:08
     */
    public List<SimpleCode> findByCategoryCdAndDeletedFalse(String categoryCd);

    /**
     * @Author: Away
     * @Title: findByCodeAndDeletedFalse
     * @Description: 按照唯一编号查找
     * @Param: code 唯一编号
     * @Return: com.laman.fusion.base.codecategory.domain.entity.SimpleCode
     * @Date: 2018/4/23 18:10
     * @Version: 2018/4/23 18:10
     */
    public SimpleCode findByCodeAndDeletedFalse(String code);
}
