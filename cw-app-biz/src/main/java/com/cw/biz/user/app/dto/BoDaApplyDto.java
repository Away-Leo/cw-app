package com.cw.biz.user.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 博达数据接口
 * Created by Administrator on 2017/9/8.
 */
@Setter
@Getter
public class BoDaApplyDto implements Serializable {

    private String batchNo;

    private List<BoDaLoanDto> data;

}
