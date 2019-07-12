package com.cw.biz.contract.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 银行卡信息
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_contract")
@Getter
@Setter
public class Contract extends AggEntity {

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品编码'")
    private Long productId;

    @Column(name="contract_url",columnDefinition="varchar(100) comment '合同保存地址'")
    private String contractUrl;

    @Column(name="express",columnDefinition="varchar(20) comment '快递公司'")
    private String express;

    @Column(name="post_no",columnDefinition="varchar(20) comment '运单号'")
    private String postNo;

    @Column(name="post_date",columnDefinition="datetime comment '邮寄时间'")
    private Date postDate;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
