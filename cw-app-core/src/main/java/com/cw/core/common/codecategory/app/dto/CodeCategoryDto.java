package com.cw.core.common.codecategory.app.dto;

import com.cw.core.common.base.BaseDto;
import lombok.Getter;
import lombok.Setter;


/**
 * @Title: CodeCategory.java
 * @Description: 下拉框树父级大类
 * @Author: Away
 * @Date: 2018/4/23 16:48
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Getter
@Setter
public class CodeCategoryDto extends BaseDto{

    /**所属大类编号**/
    private String categoryCd;

    /**所属大类名称**/
    private String categoryNm;

    /**大类描述**/
    private String description;

    /**是否为公共的0是,1否**/
    private boolean isCommon = true;

    /**是否可见 0可见,1不可见'**/
    private boolean isEnabled = true;

    /**是否为树形结构 0是,1不是'**/
    private boolean isTree = false;

}
