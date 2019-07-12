package com.cw.biz.integral.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 成功邀请人员明细
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_user_integral_invite")
@Setter
@Getter
public class IntegralInvite extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户编码'")
    private Long userId;

    @Column(name="phone",columnDefinition="varchar(11) not null comment '手机号码'")
    private String phone;

    @Column(name="loan_amount",columnDefinition="decimal(20,2) default 0 comment '下款金额'")
    private BigDecimal loanAmount=BigDecimal.ZERO;

    @Column(name="loan_date",columnDefinition="datetime comment '下款时间'")
    private Date loanDate;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
