package com.cw.core.common.codecategory.app.dto;

import com.cw.core.common.base.BaseDto;


/**
 * @Title: SimpleCode.java
 * @Description: 系统基本组件-下拉框数据字典
 * @Author: Away
 * @Date: 2018/4/23 17:44
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public class SimpleCodeDto extends BaseDto{

    /**是否为树形结构 0是,1不是**/
    private boolean isTree = false;

    /**选项编号**/
    private String code;

    /**选项名称**/
    private String name;

    /**选项全编号**/
    private String fullCode;

    /**选项描述**/
    private String description;

    /**显示顺序**/
    private Integer dispOrder;

    /**是否为默认0是,1否**/
    private boolean isDefault = false;

    /**是否为固定的 0是,1否'**/
    private boolean isFixed = false;

    /**所属大类编号**/
    protected String categoryCd;

    public boolean isTree() {
        return isTree;
    }

    public void setTree(boolean tree) {
        isTree = tree;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }
}
