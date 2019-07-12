package com.cw.biz.contract.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 合同数据传输对象
 * Created by dujiangyu on 2017/11/1.
 */
@Getter
@Setter
public class ContractDto extends BaseDto {

    private Long id;

    private Long productId;

    private String contractUrl;

    private String accountName;

    private String productName;
    private String invoiceLinkAddress;

    private String invoiceLinkPhone;

    private String express;

    private String postNo;

    private Date postDate;

    private String postDateStr;
}
