package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 产品排序日志记录
 * Created by Administrator on 2017/8/24.
 */
@Entity
@Table(name="cw_product_sort_record")
@Getter
@Setter
public class ProductSortRecord extends AggEntity{

    @Column(name="sortDate",columnDefinition="datetime comment '产品排序时间'")
    private Date sortDate;

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品ID'")
    private Long productId;

    @Column(name="product_name",columnDefinition="varchar(50) not null comment '产品名称'")
    private String productName;

    //秒必贷排序
    @Column(name="show_order",columnDefinition="int(11) comment '排序'")
    private Integer showOrder;
    //借多少排序
    @Column(name="show_order_jds",columnDefinition="int(11) comment '借多少排序'")
    private Integer showOrderJds;
    //讯闪贷排序
    @Column(name="show_order_xsd",columnDefinition="int(11) comment '讯闪贷排序'")
    private Integer showOrderXsd;
    //借钱帝排序
    @Column(name="show_order_jqw",columnDefinition="int(11) comment '借钱帝排序'")
    private Integer showOrderJqw;
    //贷款钱包排序
    @Column(name="show_order_dkqb",columnDefinition="int(11) comment '贷款钱包排序'")
    private Integer showOrderDkqb;
    //借钱乐乐排序
    @Column(name="show_order_dsqb",columnDefinition="int(11) comment '借钱乐乐排序'")
    private Integer showOrderDsqb;
    //叮叮招财排序
    @Column(name="show_order_lsqd",columnDefinition="int(11) comment '叮叮招财排序'")
    private Integer showOrderLsqd;
    //贷款部落排序
    @Column(name="show_order_lyb",columnDefinition="int(11) comment '贷款部落排序'")
    private Integer showOrderLyb;

}
