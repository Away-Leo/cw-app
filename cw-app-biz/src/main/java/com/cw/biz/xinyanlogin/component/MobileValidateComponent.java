package com.cw.biz.xinyanlogin.component;

import com.alibaba.fastjson.JSONObject;
import com.cw.biz.xinyanlogin.model.BaseReqDTO;
import com.cw.biz.xinyanlogin.utils.AesUtil;
import com.cw.biz.xinyanlogin.utils.Md5Util;
import com.cw.biz.xinyanlogin.utils.RestTemplateRequest;
import com.cw.core.common.util.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.UUID;

/**
 * 本机号码认证 API 对接Demo
 * @author yingmuhuadao
 * @date 2019/4/28
 */
@Slf4j
public class MobileValidateComponent {
    /**
     * 应用唯一标识
     * 替换为商户自己在开发者平台上面的appKey
     */
    public static String appKey = "";

    /**
     * 新颜授权token
     * 用户需要替换为预下单获取的oclToken
     */
    public static String oclToken = "";

    /**
     * 需要认证的手机号码
     */
    public static String phoneNum = "";

    /**
     * 用户需要替换为自己在开发者平台配置的密钥
     */
    public static String key = "";

    /**
     * 请求URL
     */
    public static String url = "https://dfp.xinyan-ai.com/gateway/dfpocl/ocl/validate/v1/mobileValidate";

    public static JSONObject validateMobile() {
        BaseReqDTO reqDTO = new BaseReqDTO();
        reqDTO.setAppKey(appKey);
        reqDTO.setDataType("json");
        reqDTO.setEncryptType("AES");
        reqDTO.setTimestamp(Instant.now().toString());
        reqDTO.setNonce(UUID.randomUUID().toString());
        JSONObject json = new JSONObject();
        json.put("oclToken",oclToken);
        json.put("phoneNum",phoneNum);
        String encrypt = AesUtil.encrypt(json.toJSONString(), key);
        reqDTO.setDataContent(encrypt);
        reqDTO.setDataContent(encrypt);
        String param = StringUtils.join(appKey,"&",reqDTO.getNonce(), "&", reqDTO.getTimestamp(), "&",reqDTO.getDataContent(), "&", key);
        reqDTO.setSignature(Md5Util.encrypt32(param));

        String request = RestTemplateRequest.request(JSONObject.toJSONString(reqDTO), url);
        if(ObjectHelper.isNotEmpty(request)){
            return JSONObject.parseObject(request);
        }else{
            return null;
        }

    }
}
