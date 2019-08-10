package com.cw.biz.discount.domain.service;

import com.cw.biz.discount.app.dto.ChannelUserIpDto;
import com.cw.biz.discount.domain.entity.ChannelUserIp;
import com.cw.biz.discount.domain.repository.ChannelUserIpRepository;
import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.util.DateHelper;
import com.cw.core.common.util.ObjectHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@Slf4j
public class ChannelUserIpDomainService extends BaseDomainService<ChannelUserIpRepository,ChannelUserIp, ChannelUserIpDto> {


    public ChannelUserIpDto findByCodeAndIp(ChannelUserIpDto channelUserIpDto){
        if(ObjectHelper.isNotEmpty(channelUserIpDto)){
            ChannelUserIp sourceData=this.CT.findByChannelCodeAndUserIp(channelUserIpDto.getChannelCode(),channelUserIpDto.getUserIp());
            return ObjectHelper.isNotEmpty(sourceData)?sourceData.to(ChannelUserIpDto.class):null;
        }else{
            return null;
        }
    }

    private ChannelUserIpDto saveChannelUserIp(String ip,String code) {
        if(ObjectHelper.isNotEmpty(code)&&ObjectHelper.isNotEmpty(ip)){
            ChannelUserIp old=this.CT.findByChannelCodeAndUserIp(code,ip);
            if(ObjectHelper.isEmpty(old)){
                ChannelUserIp channelUserIp=new ChannelUserIp();
                channelUserIp.setChannelCode(code);
                channelUserIp.setUserIp(ip);
                channelUserIp.setFlowTime(DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtShort)));
                return this.CT.saveEntity(channelUserIp).to(ChannelUserIpDto.class);
            }else{
                return null;
            }
        }else {
            return null;
        }
    }

    public Long findByCodeAndTime(String code,String time){
        if(ObjectHelper.isNotEmpty(code)&&ObjectHelper.isNotEmpty(time)){
            return this.CT.findByChannelCodeAndFlowTime(code, time);
        }else{
            return 0L;
        }
    }

    public ChannelUserIpDto saveDataFromRequest(HttpServletRequest request,ChannelUserIpDto channelUserIpDto){
        String ip=getIP(request);
        return this.saveChannelUserIp(ip,channelUserIpDto.getChannelCode());
    }

    private String getIP(HttpServletRequest request){
        log.info("X-Real-IP：{}---------------x-forwarded-for:{}",request.getHeader("X-Real-IP"),request.getHeader("x-forwarded-for"));
        if(ObjectHelper.isNotEmpty(request)){
            String ip=request.getHeader("x-forwarded-for");
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("Proxy-Client-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("WL-Proxy-Client-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getHeader("X-Real-IP");
            }
            if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
                ip=request.getRemoteAddr();
            }
            return ip;
        }else{
            return null;
        }
    }
}
