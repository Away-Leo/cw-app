
package com.cw.core.common.base;

import com.cw.core.common.util.ObjectProperUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: BaseDto.java
 * @Description:  基本数据传输体
 * @Author: Away
 * @Date: 2018/4/12 14:32
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public abstract class BaseDto implements Serializable {

    public BaseDto() {
       this.appName=System.getProperty("appName");
    }

    public String appName;

    /**主键ID**/
    private Long id;

    /**逗号隔开多个ID**/
    private String ids;


    /**创建时间**/
    private Date rawAddTime = null;

    /**修改时间**/
    private Date rawUpdateTime;

    /**创建人**/
    private String rawCreator = "1";

    /**修改人**/
    private String rawModifier = "1";

    private Integer page;

    private Integer size =10;

    /**
     * @Author: Away
     * @Description: 转换为相应的域对象
     * @Param: tClass
     * @Return T
     * @Date 2018/1/11 11:35
     * @Copyright 重庆平迅数据服务有限公司
     */
    public <T> T toEntity(Class<T> tClass) throws Exception {
        T returnData = tClass.newInstance();
        returnData = (T) ObjectProperUtil.compareAndValue(this, returnData, true, null);
        return returnData;
    }

    public String getIds() {
        return ids;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRawAddTime() {
        return rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public String getRawCreator() {
        return rawCreator;
    }

    public void setRawCreator(String rawCreator) {
        this.rawCreator = rawCreator;
    }

    public String getRawModifier() {
        return rawModifier;
    }

    public void setRawModifier(String rawModifier) {
        this.rawModifier = rawModifier;
    }

}