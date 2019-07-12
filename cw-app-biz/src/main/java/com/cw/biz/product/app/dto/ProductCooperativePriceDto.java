package com.cw.biz.product.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.core.common.util.Utils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品合作价格
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductCooperativePriceDto extends BaseDto{

    private Long id;

    private String cooperationModel;

    private Long productId;

    private Integer startNum;

    private Integer endNum;

    private String priceType;

    private BigDecimal price=BigDecimal.ZERO;

    private String startCycleStr;

    private String endCycleStr;

    private Date startCycle;

    private Date endCycle;

    public Date getStartCycle() {
        if(Utils.strConvertDate(startCycleStr)==null){
           return startCycle;
        }
        return Utils.strConvertDate(startCycleStr);
    }

    public Date getEndCycle() {
        if(Utils.strConvertDate(endCycleStr)==null){
            return endCycle;
        }
        return Utils.strConvertDate(endCycleStr);
    }
}
