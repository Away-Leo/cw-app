package com.cw.web.front.controller.download;

import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.app.service.LogAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * 连接下载app
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class DownloadApkController {

    private Logger logger = LoggerFactory.getLogger(DownloadApkController.class);

    @Autowired
    private LogAppService logAppService;
    public static  String appName = "MBD";
    //苹果商店
    public static  String appleStore = "https://itunes.apple.com/us/app/%E5%B9%B3%E8%AE%AF%E7%A7%92%E5%BF%85%E8%B4%B7/id1244692810?mt=8";
    //应用宝
    public static  String yingyongbao_androidStore = "http://a.app.qq.com/o/simple.jsp?pkgname=com.pingxun.activity";
    //小米
    public static String xiaomi_androidStore="http://app.mi.com/details?id=com.pingxun.activity";
    //华为
    public static String huawei_androidStore="http://appstore.huawei.com/app/C100029519";
    //oppo应用市场
    public static String oppo_androidStore="http://store.oppomobile.com/product/0011/070/674_1.html?from=1152_1";
    //搜狗应用市场
    public static String sougou_androidStore="http://zhushou.sogou.com/apps/detail/638371.html";
    //乐视应用市场
    public static String leshi_androidStore="http://mobile.leplay.cn/app/22081025";
    //vivo应用市场
    public static String vivo_androidStore="http://info.appstore.vivo.com.cn/detail/1858443?source=7";
    //锤子应用市场
    public static String smartisan_androidStore="http://zhushou.sogou.com/apps/detail/638371.html";
    /**
     * 下载链接
     * @param channelNo
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/api/downloadapk")
    public String downloadApk(String channelNo, HttpServletRequest request, HttpServletResponse response)
    {
        //记录渠道下载数据
        String deviceInfo = request.getHeader("user-agent");
        String sysName = System.getProperty("os.name");
        downloadLog(channelNo,deviceInfo,1);
        //下载apk
        OutputStream out = null ;
        InputStream in = null ;
        try {
            //记录渠道点击记录
            String head = request.getHeader("user-agent");
            logger.info("channelNo = " + channelNo);
            logger.info("head = " + head);

            String fileName = "miaobidai.apk";
            //下载机器码文件
//            response.setContentType("application/vnd.android.package-archive");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("ISO-8859-1"), "UTF-8"));
            response.setHeader("conent-type", "application/vnd.android.package-archive");

            out = response.getOutputStream();
            if(sysName.contains("Window")) {
                in = new FileInputStream("D://var//jieqiandaiapp.apk");
            }else{
                in = new FileInputStream("//home//pingxun//"+channelNo+"app.apk");
            }
            //写文件
            int b;
            while((b=in.read())!= -1)
            {
                out.write(b);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "ok";
    }

    /**
     * 记录下载日志
     * @param channelNo
     * @param deviceInfo
     */
    private void downloadLog(String channelNo,String deviceInfo,Integer type)
    {
        LogDto logDto = new LogDto();
        logDto.setApplyDate(new Date());
        logDto.setChannelNo(channelNo);
        if(type==1) {
            logDto.setType(LogEnum.DOWNLOAD_APK);
        }else{
            logDto.setType(LogEnum.APPLICATION_LINK);
        }
        logDto.setAppName(appName);
        if(deviceInfo.length()>500)
        {
            deviceInfo = deviceInfo.substring(0,500);
        }
        logDto.setDeviceNumber(deviceInfo);//记录设备信息
        logAppService.create(logDto);
    }


    /**
     * app市场应用链接
     * @param channelNo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/api/appLinkJump")
    public String appLinkJump(String channelNo,String client,HttpServletRequest request, HttpServletResponse response)
    {
        //记录渠道下载数据
        String deviceInfo = request.getHeader("user-agent");
        try {
            //记录渠道点击记录
            String head = request.getHeader("user-agent");
            if(head!=null)
            {
                head = head.toLowerCase();
                String appMarket="";
                if(head.indexOf("iphone")!=-1){
                    appMarket=appleStore;
                }else if(head.indexOf("honor")!=-1){
                    appMarket=huawei_androidStore;
                }else if(head.indexOf("oppo")!=-1){
                    appMarket = oppo_androidStore;
                }else if(head.indexOf("leshi")!=-1){
                    appMarket=vivo_androidStore;
                }else if(head.indexOf("vivo")!=-1){
                    appMarket=vivo_androidStore;
                }else if(head.indexOf("smartisan")!=-1){
                    appMarket=smartisan_androidStore;
                }else if(head.indexOf("redmi")!=-1||head.indexOf("MI ")!=-1){
                    appMarket=xiaomi_androidStore;
                }else{
                    appMarket=yingyongbao_androidStore;
                }
                if("h5".equals(client)) {
                    appMarket = "https://www.pingxundata.com/topic/invite/invite.html?channelNo=" + channelNo;
                }
                //记录日志
                downloadLog(channelNo,appMarket,2);
                //跳转应用市场
                response.sendRedirect(appMarket);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

        }
        return null;
    }


}
