package com.cw.biz.user.domain.entity;

import com.cw.core.common.base.BaseEntity;
import com.cw.core.common.base.CwException;
import com.zds.common.lang.beans.Copier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="pf_se_user",indexes = {@Index(columnList = "wechat_id,merchant_id",name = "idx_wid_mid"),@Index(columnList = "phone",name = "index_phone"),@Index(columnList = "source_code",name = "source")})
@Getter
@Setter
public class UserEntity extends BaseEntity{


    @Column(name="merchant_id",columnDefinition="int(11) unique  comment '商户ID'")
    private Long merchantId;

    @Column(name="username",columnDefinition="varchar(100) unique NOT NULL  comment '用户名'")
    private String username;

    @Column(name="display_name",columnDefinition="varchar(100) comment ''")
    private String displayName;

    @Column(name="password",columnDefinition="varchar(256) comment ''")
    private String password;

    @Column(name="salt",columnDefinition="varchar(100) comment ''")
    private String salt;

    @Column(name="role_ids",columnDefinition="varchar(100) comment ''")
    private String roleIds;

    @Column(name="locked",columnDefinition="tinyint(1) comment ''")
    private Boolean locked;

    @Column(name="actived",columnDefinition="tinyint(1) comment '是否下载APP登陆'")
    private Boolean actived;

    @Column(name="type",columnDefinition="varchar(100) comment ''")
    private String type;


    @Column(name="rid",columnDefinition="int(11) comment ''")
    private Long rid;

    @Column(name="wechat_id",columnDefinition="varchar(100) comment ''")
    private String wechatId;

    @Column(name="phone",columnDefinition="varchar(30) comment ''")
    private String phone;

    @Column(name="register_date",columnDefinition="datetime comment ''")
    private Date registerDate;

    @Column(name="source_code",columnDefinition="varchar(255) comment ''")
    private String sourceCode;

    @Column(name="activedevice",columnDefinition="varchar(255) comment '登录APP设备'")
    private String activedevice;

}
