package com.cw.biz.user.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.user.app.enums.EducationEnum;
import com.cw.biz.user.app.enums.IdentityEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息完善类
 * Created by dujy on 2017/7/31.
 */
@Getter
@Setter
public class CwUserInfoDto extends BaseDto{

    private Long id;

    private Long userId;

    private String name;

    private String phone;

    private String ids;

    private String certNo;
    //博达数据批次号
    private String batchNo;

    private String city;

    private String address;

    private String sex;

    private String idCardFace;

    private String idCardBack;

    private Date birthday;

    private Integer age;
    //默认一次导入数据100条
    private Integer exportNum=100;

    private String domicilePlace;

    private String education;

    private String socialIdentity;

    private BigDecimal income;

    private Boolean isSocial=Boolean.FALSE;

    private Boolean isFund=Boolean.FALSE;

    private Boolean isCreditCard=Boolean.FALSE;

    private Boolean isHouseOrCarLoan=Boolean.FALSE;

    private Boolean isCarLoan=Boolean.FALSE;

    private BigDecimal wechatAmount= BigDecimal.ZERO;

    private BigDecimal sesameCredit=BigDecimal.ZERO;

}
