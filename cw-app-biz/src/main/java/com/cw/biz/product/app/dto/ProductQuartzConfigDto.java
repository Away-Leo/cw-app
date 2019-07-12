package com.cw.biz.product.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/22.
 */
@Setter
@Getter
public class ProductQuartzConfigDto {

    private Long id;

    private Date onlineDate;

    private Date offlineDate;

    private Long productId;

    private String memo;
}
