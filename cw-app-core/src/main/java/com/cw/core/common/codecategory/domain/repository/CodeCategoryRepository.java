package com.cw.core.common.codecategory.domain.repository;

import com.cw.core.common.base.BaseRepository;
import com.cw.core.common.codecategory.domain.entity.CodeCategory;

/**
 * @Title: CodeCategoryRepository.java
 * @Description: 树形菜单父级大类操作库
 * @Author: Away
 * @Date: 2018/4/23 16:57
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public interface CodeCategoryRepository extends BaseRepository<CodeCategory,Long> {

    /**
     * @Author: Away
     * @Title: deleteByCategoryCd
     * @Description: 按照大类唯一编号删除
     * @Param: categoryCd
     * @Return: void
     * @Date: 2018/4/24 16:16
     * @Version: 2018/4/24 16:16
     */
    public void deleteByCategoryCd(String categoryCd);
}
