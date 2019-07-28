package com.cw.core.common.base;

import com.zds.common.lang.beans.Copier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'")
    protected Long id;

    @Column(name = "raw_add_time", columnDefinition = " timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    protected Date rawAddTime;

    @Column(name = "raw_update_time", columnDefinition = "datetime  COMMENT '修改时间'")
    protected Date rawUpdateTime;

    @Column(columnDefinition = " tinyint default 0  COMMENT '逻辑删除状态0未删除,1已删除' ")
    private boolean deleted = false;

    @Column(name = "raw_creator", columnDefinition = "varchar(20) comment '创建人'")
    protected String rawCreator;

    @Column(name = "raw_modifier", columnDefinition = "varchar(20)  null  comment '修改人'")
    protected String rawModifier;

    @Version
    @Column(name = "raw_version", columnDefinition = "smallint(6) unsigned  DEFAULT '1' COMMENT '版本号'")
    protected Short rawVersion = 1;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRawAddTime() {
        return this.rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return this.rawUpdateTime;
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

    public Short getRawVersion() {
        return rawVersion;
    }

    public void setRawVersion(Short rawVersion) {
        this.rawVersion = rawVersion;
    }



    /**
     * 转换领域对象为目标类型
     *
     * @param clazz 目标类型
     */
    public <T> T to(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            Copier.copy(this, t, Copier.CopyStrategy.IGNORE_NULL, Copier.NoMatchingRule.IGNORE);
            return t;
        } catch (Exception e) {
            CwException.throwIt(e);
            return null;
        }
    }

}
