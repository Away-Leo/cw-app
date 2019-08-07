package com.cw.biz.xinyanlogin.component;

import com.alibaba.fastjson.JSONObject;
import com.cw.biz.xinyanlogin.model.BaseReqDTO;
import com.cw.biz.xinyanlogin.utils.AesUtil;
import com.cw.biz.xinyanlogin.utils.Md5Util;
import com.cw.biz.xinyanlogin.utils.RestTemplateRequest;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.UUID;

/**
 * 获取手机号 API 对接Demo
 * @author yingmuhuadao
 * @date 2019/4/28
 */
@Slf4j
public class GetMobileComponent {
    /**
     * 应用唯一标识
     * 替换为商户自己在开发者平台上面的appKey
     */
    public static String appKey = "4cb387a38b3f206f0721d247c76ecd46";


    /**
     * 用户需要替换为自己在开发者平台配置的密钥
     */
    public static String key = "d9e430e94a26939f";

    /**
     * 请求URL
     */
    public static String url = "https://dfp.xinyan-ai.com/gateway/dfpocl/ocl/oclOrder/v1/getMobile";

    public static JSONObject getMobile(String oclToken) {
        if(ObjectHelper.isNotEmpty(oclToken)){
            BaseReqDTO reqDTO = new BaseReqDTO();
            reqDTO.setAppKey(appKey);
            reqDTO.setDataType("json");
            reqDTO.setEncryptType("AES");
            reqDTO.setTimestamp(Instant.now().toString());
            reqDTO.setNonce(UUID.randomUUID().toString()+reqDTO.getTimestamp());
            JSONObject json = new JSONObject();
            json.put("oclToken",oclToken);
            String encrypt = AesUtil.encrypt(json.toJSONString(), key);
            reqDTO.setDataContent(encrypt);
            reqDTO.setDataContent(encrypt);
            String param = StringUtils.join(appKey,"&",reqDTO.getNonce(), "&", reqDTO.getTimestamp(), "&",
                    reqDTO.getDataContent(), "&", key);
            reqDTO.setSignature(Md5Util.encrypt32(param));

            String request = RestTemplateRequest.request(JSONObject.toJSONString(reqDTO), url);

            if(ObjectHelper.isNotEmpty(request)){
                return JSONObject.parseObject(request);
            }else{
                throw new BusinessException(ENUM_EXCEPTION.E10032.code,ENUM_EXCEPTION.E10032.msg);
            }
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }
}
