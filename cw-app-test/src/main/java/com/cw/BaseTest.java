package com.cw;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.service.MarketDailyAppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Main.class)// 指定spring-boot的启动类
public class BaseTest{

    private Logger log = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    MarketDailyAppService appService;

    @Test
    public void updateMerchantIncome()  {
        ReportSearchDto dto = new ReportSearchDto();
        List<MarketDailyDto> ss= appService.marketDailyDetailTest(dto);
        for (MarketDailyDto dailyDto:ss) {
            log.info(ss.toString() + "" + dailyDto.getProductName());
            appService.updateMerchantIncome(dailyDto);
        }
    }

}

