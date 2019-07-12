package com.cw.biz.home.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 贷款统计数据
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="cw_loan_service")
@Getter
@Setter
public class LoanService extends AggEntity {

    @Column(name="service_user_num",columnDefinition="int(11)  comment '累计服务人数'")
    private Integer serviceUserNum;

    @Column(name="loan_total_amount",columnDefinition="decimal(16,2)  comment '贷款金额'")
    private BigDecimal loanTotalAmount;

    @Column(name="success_rate",columnDefinition="decimal(16,2)  comment '成功率'")
    private BigDecimal successRate=BigDecimal.ZERO;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}