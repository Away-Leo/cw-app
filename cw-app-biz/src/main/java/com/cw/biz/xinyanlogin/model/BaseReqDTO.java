package com.cw.biz.xinyanlogin.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 基础请求参数
 * @author : hao_xu 2019-01-18
 *
 */
@Data
public class BaseReqDTO implements Serializable {

    private static final long serialVersionUID = -7874010342121832587L;

    /**
     * 应用唯一标识
     */
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /**
     * 加密类型AES
     */
    @NotBlank(message = "encryptType不能为空")
    private String encryptType = "AES";

    /**
     * 数据类型
     */
    @NotBlank(message = "dataType不能为空")
    private String dataType = "json";

    /**
     * 加密数据json
     */
    @NotBlank(message = "dataContent不能为空")
    private String dataContent;

    /**
     * 签名结果串
     */
    @NotBlank(message = "signature不能为空")
    private String signature;

    /**
     * 请求时间戳
     */
    @NotBlank(message = "timestamp不能为空")
    private String timestamp;

    /**
     * 唯一随机数
     */
    @NotBlank(message = "nonce不能为空")
    private String nonce;
}
