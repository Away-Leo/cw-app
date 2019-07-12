package com.cw.biz.goods.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品邮寄地址
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_post_address")
@Getter
@Setter
public class PostAddress extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11)  comment '用户ID'")
    private Long userId;

    @Column(name="name",columnDefinition="varchar(200)  comment '收件人姓名'")
    private String name;

    @Column(name="phone",columnDefinition="varchar(11) comment '联系电话'")
    private String phone;

    @Column(name="address",columnDefinition="varchar(300)  comment '收件地址'")
    private String address;

    @Column(name="is_default",columnDefinition="tinyint(1)  comment '是否默认'")
    private Boolean isDefault;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
