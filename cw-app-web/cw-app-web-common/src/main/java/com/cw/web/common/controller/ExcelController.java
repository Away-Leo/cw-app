package com.cw.web.common.controller;

import com.cw.biz.home.app.dto.*;
import com.cw.biz.home.app.service.HomeAppService;
import com.cw.biz.product.app.dto.ProductDto;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductSearchDto;
import com.cw.biz.product.app.service.ProductAppService;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.service.MarketDailyAppService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("excel")
public class ExcelController {

    @Autowired
    private ProductAppService productAppService;
    @Autowired
    private HomeAppService homeAppService;
    @Autowired
    private MarketDailyAppService marketDailyAppService;

    public static Map fileName= new HashMap();
    public static String[] productAry = new String[]{"序号","来源渠道","产品名称","点击次数","申请次数","合作类型","合作价格","状态"};
    public static String[] productCountAry = new String[]{"序号","产品名称","申请次数","申请用户数","合作类型","合作价格","是否下载APP","上线时间"};
    public static String[] appMarketAry = new String[]{"序号","渠道名称","注册用户数","申请次数","申请用户数"};
    public static String[] channelAry = new String[]{"序号","渠道名称","渠道编码","注册用户数","申请次数","申请用户数"};
    public static String[] marketIncomeAry = new String[]{"序号","公司名称","产品名称","合作类型","申请次数","申请用户数","CPA价格","CPS价格"
            ,"CPA数","CPS数","下款额","均单值","CPA收入","CPA转化率(%)","CPS收入","CPS转化率(%)","总收入","千人总收入","千人成本","入库时间"};
    public static String[] incomeMonthAry = new String[]{"序号","公司名称","产品名称","合作类型","申请次数","申请用户数","CPA价格","CPS价格"
            ,"CPA数","CPS数","下款额","均单值","CPA收入","CPA转化率(%)","CPS收入","CPS转化率(%)","开票税费","总收入","回款金额","回款备注","入库时间"};
    public static String[] operateAry = new String[]{"日期",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均",
            "合计","IOS","安卓","H5","人数","次数","人均"};
    public static String[] appTotalAry = new String[]{"日期",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它",
            "华为","小米","VIVO","OPPO","360","应用宝","苹果","魅族","豌豆荚","百度","其它"};

    public static String[] storeDetailAry = new String[]{"序号","商户名称","金融产品","来量渠道","来量产品","申请次数","申请用户数"};
    public static String[] storeTotalAry = new String[]{"序号","商户名称","金融产品","申请次数","申请用户数"};
    public static String[] appUserAry = new String[]{"App名称","新增用户数","申请次数","申请用户数"};

    static {
        fileName.put("productList","产品上线列表");
        fileName.put("productCount","产品申请统计");
        fileName.put("appMarket","应用市场来量统计表");
        fileName.put("channel","渠道来量");
        fileName.put("operate","运营汇总");
        fileName.put("storeDetail","商户来量明细");
        fileName.put("storeTotal","商户来量统计");
        fileName.put("appUser","APP用户分布");
        fileName.put("appTotal","应用市场汇总");
        fileName.put("incomeMonth","收入月报");
    }
    /***
     * 创建表头
     * @param workbook
     * @param sheet
     */
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet,String[] title){
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        for(int i=0;i<title.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
    }

    /**
     * 运营报表title
     * @param workbook
     * @param sheet
     * @param title
     */
    private void createOperateTitle(HSSFWorkbook workbook, HSSFSheet sheet,String[] title){
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFRow row1 = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);
        HSSFCell cell1;
        cell1 = row1.createCell(0);
        cell1.setCellValue("日期");
        cell1.setCellStyle(style);

        cell1 = row1.createCell(1);
        cell1.setCellValue("秒必贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 1, (short)7));

        cell1 = row1.createCell(8);
        cell1.setCellValue("借钱帝");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 8, (short)14));

        cell1 = row1.createCell(15);
        cell1.setCellValue("讯闪贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 15, (short)21));

        cell1 = row1.createCell(22);
        cell1.setCellValue("借多少");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 22, (short)28));

        cell1 = row1.createCell(29);
        cell1.setCellValue("借钱乐乐");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 29, (short)35));

        cell1 = row1.createCell(36);
        cell1.setCellValue("贷款哒");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 36, (short)42));

        cell1 = row1.createCell(43);
        cell1.setCellValue("借钱贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 43, (short)49));

        cell1 = row1.createCell(50);
        cell1.setCellValue("贷款帝");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 50, (short)56));

        cell1 = row1.createCell(57);
        cell1.setCellValue("贷款部落");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 57, (short)63));

        cell1 = row1.createCell(64);
        cell1.setCellValue("叮叮招财");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 64, (short)70));

        cell1 = row1.createCell(71);
        cell1.setCellValue("速亿贷(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 71, (short)77));

        cell1 = row1.createCell(78);
        cell1.setCellValue("速融贷(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 78, (short)84));

        cell1 = row1.createCell(85);
        cell1.setCellValue("贷款掌柜(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 85, (short)91));

        cell1 = row1.createCell(92);
        cell1.setCellValue("借借款(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 92, (short)98));

        cell1 = row1.createCell(99);
        cell1.setCellValue("贷钱来(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 99, (short)105));

        cell1 = row1.createCell(106);
        cell1.setCellValue("借贷帮(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 106, (short)112));

        cell1 = row1.createCell(113);
        cell1.setCellValue("借钱花花(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 113, (short)119));

        cell1 = row1.createCell(120);
        cell1.setCellValue("快借王(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 120, (short)126));

        cell1 = row1.createCell(127);
        cell1.setCellValue("贷款神速(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 127, (short)133));

        cell1 = row1.createCell(134);
        cell1.setCellValue("借钱富(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 134, (short)140));

        cell1 = row1.createCell(141);
        cell1.setCellValue("贷款汇(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 141, (short)147));

        cell1 = row1.createCell(148);
        cell1.setCellValue("秒贷宝(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 148, (short)154));

        cell1 = row1.createCell(155);
        cell1.setCellValue("信而贷(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 155, (short)161));

        cell1 = row1.createCell(162);
        cell1.setCellValue("借钱树(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 162, (short)168));

        cell1 = row1.createCell(169);
        cell1.setCellValue("借贷金钱(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 169, (short)175));

        cell1 = row1.createCell(176);
        cell1.setCellValue("贷款堂(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 176, (short)182));

        cell1 = row1.createCell(183);
        cell1.setCellValue("好贷宝(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 183, (short)189));

        cell1 = row1.createCell(190);
        cell1.setCellValue("易借侠(马甲)");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 190, (short)196));

        cell1 = row1.createCell(197);
        cell1.setCellValue("总量");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 197, (short)203));

        HSSFRow row2 = sheet.createRow(1);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);
        HSSFCell cell2;
        cell2 = row2.createCell(0);
        cell2.setCellValue("日期");
        cell2.setCellStyle(style);

        cell1 = row2.createCell(1);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 1, (short)4));

        cell1 = row2.createCell(5);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 5, (short)7));


        cell1 = row2.createCell(8);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 8, (short)11));

        cell1 = row2.createCell(12);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 12, (short)14));

        cell1 = row2.createCell(15);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 15, (short)18));

        cell1 = row2.createCell(19);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 19, (short)21));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(22);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 22, (short)25));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(26);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 26, (short)28));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(29);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 29, (short)32));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(33);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 33, (short)35));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(36);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 36, (short)39));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(40);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 40, (short)42));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(43);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 43, (short)46));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(47);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 47, (short)49));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(50);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 50, (short)53));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(54);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 54, (short)56));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(57);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 57, (short)60));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(61);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 61, (short)63));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(64);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 64, (short)67));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(68);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 68, (short)70));
        cell1.setCellStyle(style);


        cell1 = row2.createCell(71);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 71, (short)74));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(75);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 75, (short)77));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(78);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 78, (short)81));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(82);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 82, (short)84));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(85);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 85, (short)88));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(89);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 89, (short)91));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(92);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 92, (short)95));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(96);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 96, (short)98));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(99);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 99, (short)102));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(103);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 103, (short)105));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(106);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 106, (short)109));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(110);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 110, (short)112));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(113);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 113, (short)116));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(117);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 117, (short)119));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(120);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 120, (short)123));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(124);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 124, (short)126));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(127);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 127, (short)130));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(131);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 131, (short)133));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(134);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 134, (short)137));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(138);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 138, (short)140));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(141);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 141, (short)144));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(145);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 145, (short)147));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(148);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 148, (short)151));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(152);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 152, (short)154));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(155);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 155, (short)158));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(159);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 159, (short)161));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(162);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 162, (short)165));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(166);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 166, (short)168));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(169);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 169, (short)172));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(173);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 173, (short)175));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(176);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 176, (short)179));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(180);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 180, (short)182));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(183);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 183, (short)186));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(187);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 187, (short)189));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(190);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 190, (short)193));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(194);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 194, (short)196));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(197);
        cell1.setCellValue("基础数据");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 197, (short)200));
        cell1.setCellStyle(style);

        cell1 = row2.createCell(201);
        cell1.setCellValue("申请贷款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, (short)1, 201, (short)203));
        cell1.setCellStyle(style);

        HSSFRow row = sheet.createRow(2);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);

        HSSFCell cell;
        for(int i=0;i<title.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, (short)2, 0, (short)0));

    }

    /**
     * 应用市场统计汇总
     * @param workbook
     * @param sheet
     * @param title
     */
    private void createAppMarketTitle(HSSFWorkbook workbook, HSSFSheet sheet,String[] title){
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFRow row1 = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);
        HSSFCell cell1;
        cell1 = row1.createCell(0);
        cell1.setCellValue("日期");
        cell1.setCellStyle(style);

        cell1 = row1.createCell(1);
        cell1.setCellValue("秒必贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 1, (short)11));

        cell1 = row1.createCell(12);
        cell1.setCellValue("借钱帝");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 12, (short)22));

        cell1 = row1.createCell(23);
        cell1.setCellValue("讯闪贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 23, (short)33));

        cell1 = row1.createCell(34);
        cell1.setCellValue("借多少");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 34, (short)44));

        cell1 = row1.createCell(45);
        cell1.setCellValue("借钱乐乐");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 45, (short)55));

        cell1 = row1.createCell(56);
        cell1.setCellValue("贷款哒");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 56, (short)66));

        cell1 = row1.createCell(67);
        cell1.setCellValue("借钱贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 67, (short)77));

        cell1 = row1.createCell(78);
        cell1.setCellValue("贷款帝");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 78, (short)88));

        cell1 = row1.createCell(89);
        cell1.setCellValue("贷款部落");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 89, (short)99));

        cell1 = row1.createCell(100);
        cell1.setCellValue("叮叮招财");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 100, (short)110));

        cell1 = row1.createCell(111);
        cell1.setCellValue("速亿贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 111, (short)121));

        cell1 = row1.createCell(122);
        cell1.setCellValue("速融贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 122, (short)132));

        cell1 = row1.createCell(133);
        cell1.setCellValue("贷钱来");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 133, (short)143));

        cell1 = row1.createCell(144);
        cell1.setCellValue("借贷帮");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 144, (short)154));

        cell1 = row1.createCell(155);
        cell1.setCellValue("贷款掌柜");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 155, (short)165));

        cell1 = row1.createCell(166);
        cell1.setCellValue("借借款");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 166, (short)176));

        cell1 = row1.createCell(177);
        cell1.setCellValue("借钱花花");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 177, (short)187));

        cell1 = row1.createCell(188);
        cell1.setCellValue("快借王");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 188, (short)198));

        cell1 = row1.createCell(199);
        cell1.setCellValue("贷款神速");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 199, (short)209));

        cell1 = row1.createCell(210);
        cell1.setCellValue("借钱富");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 210, (short)220));

        cell1 = row1.createCell(221);
        cell1.setCellValue("贷款汇");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 221, (short)231));

        cell1 = row1.createCell(232);
        cell1.setCellValue("秒贷宝");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 232, (short)242));

        cell1 = row1.createCell(243);
        cell1.setCellValue("信而贷");
        cell1.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 243, (short)253));

//        cell1 = row1.createCell(254);
//        cell1.setCellValue("借钱树");
//        cell1.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 254, (short)264));
//
//        cell1 = row1.createCell(265);
//        cell1.setCellValue("借贷金钱");
//        cell1.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 265, (short)275));
//
//        cell1 = row1.createCell(276);
//        cell1.setCellValue("贷款堂");
//        cell1.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 276, (short)286));
//
//        cell1 = row1.createCell(287);
//        cell1.setCellValue("好贷宝");
//        cell1.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 287, (short)297));
//
//        cell1 = row1.createCell(298);
//        cell1.setCellValue("易借侠");
//        cell1.setCellStyle(style);
//        sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 298, (short)308));

        HSSFRow row = sheet.createRow(1);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);

        HSSFCell cell;
        for(int i=0;i<title.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        sheet.addMergedRegion(new CellRangeAddress(0, (short)1, 0, (short)0));

    }

    /***
     * 获取excel数据
     * @return 返回文件名称及excel文件的URL
     * @throws IOException
     */
    @RequestMapping("exportExcel")
    public String exportExcel(HttpServletResponse response, String excelFlag, ReportSearchDto reportSearchDto) throws IOException{
        try{
             String exportFileName=fileName.get(excelFlag).toString();
             ServletOutputStream outputStream = response.getOutputStream();
             String extFileName = new String(exportFileName.getBytes(), "ISO8859_1");
             response.setHeader("Content-disposition", "attachment; filename=" + extFileName + ".xls");

             HSSFWorkbook workbook = new HSSFWorkbook();
             HSSFSheet sheet = workbook.createSheet(exportFileName);

             //设置日期格式
             HSSFCellStyle style=workbook.createCellStyle();
             style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

             //构建表格标题
             checkExcelType(workbook,sheet,excelFlag,reportSearchDto);
             workbook.write(outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据标识
     * @param workbook
     * @param sheet
     * @param excelFlag
     * @return
     */
    private void checkExcelType(HSSFWorkbook workbook,HSSFSheet sheet,String excelFlag,ReportSearchDto reportSearchDto){
        //默认是产品上线excel导出
        switch (excelFlag)
        {
            case "appMarket"://构建产品导出title
                createTitle(workbook, sheet,appMarketAry);
                createAppMarketData(sheet,reportSearchDto);
                break;
            case "channel":
                createTitle(workbook, sheet,channelAry);
                createChannelData(sheet,reportSearchDto);
                break;
            case "operate":
                createOperateTitle(workbook, sheet,operateAry);
                createOperateData(workbook,sheet,reportSearchDto);
                break;
            case "storeDetail":
                createTitle(workbook, sheet,storeDetailAry);
                createDetailData(sheet,reportSearchDto);
                break;
            case "storeTotal":
                createTitle(workbook, sheet,storeTotalAry);
                createTotalData(sheet,reportSearchDto);
                break;
            case "appUser":
                createTitle(workbook, sheet,appUserAry);
                createAppUserData(sheet,reportSearchDto);
                break;
            case "appTotal":
                createAppMarketTitle(workbook, sheet,appTotalAry);
                createAppTotalData(workbook,sheet,reportSearchDto);
                break;
            case "productCount"://产品统计
                createTitle(workbook, sheet,productCountAry);
                createProductCountData(sheet,reportSearchDto);
                break;
            case "marketIncomeDaily"://收入统计
                createTitle(workbook, sheet,marketIncomeAry);
                createIncomeData(sheet,reportSearchDto);
                break;
            case "incomeMonth":
                createTitle(workbook, sheet,incomeMonthAry);
                createIncomeMonthData(sheet,reportSearchDto);
                break;
            default:
                createTitle(workbook, sheet,productAry);
                createData(sheet,reportSearchDto);
        }
    }

    /**
     * 产品上线列表
     * @param sheet
     */
    private void createProductCountData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        ProductSearchDto productSearchDto = new ProductSearchDto();
        productSearchDto.setApplyStartDate(reportSearchDto.getApplyStartDate());
        productSearchDto.setApplyEndDate(reportSearchDto.getApplyEndDate());
        productSearchDto.setName(reportSearchDto.getProductName());
        productSearchDto.setBelongApp(reportSearchDto.getBelongApp());
        productSearchDto.setIsValid(reportSearchDto.getIsValid());
        productSearchDto.setCooperationModel(reportSearchDto.getCooperationModel());
        List<ProductListDto> entities = homeAppService.productCount(productSearchDto);
        for (ProductListDto productListDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(productListDto.getId());
            row.createCell(1).setCellValue(productListDto.getName());
            row.createCell(2).setCellValue(productListDto.getClickNum());
            row.createCell(3).setCellValue(productListDto.getApplyNum());
            row.createCell(4).setCellValue(productListDto.getCooperationModel());
            row.createCell(5).setCellValue(productListDto.getUnitPrice());
            row.createCell(6).setCellValue(productListDto.getIsDownloadApp()?"是":"否");
            row.createCell(7).setCellValue(productListDto.getOnlineDate()+"");
            rowNum++;
        }
    }

    /**
     * 收入数据
     * @param sheet
     * @param reportSearchDto
     */
    private void createIncomeData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<MarketDailyDto> entities = marketDailyAppService.findAll(reportSearchDto);
        for (MarketDailyDto marketDailyDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(marketDailyDto.getId());
            row.createCell(1).setCellValue(marketDailyDto.getEnterpriseName());
            row.createCell(2).setCellValue(marketDailyDto.getProductName());
            row.createCell(3).setCellValue(marketDailyDto.getCooperationModel());
            row.createCell(4).setCellValue(marketDailyDto.getApplyTime());
            row.createCell(5).setCellValue(marketDailyDto.getApplyNum());
            row.createCell(6).setCellValue(marketDailyDto.getPriceA()+"");
            row.createCell(7).setCellValue(marketDailyDto.getPriceS()+"");

            row.createCell(8).setCellValue(marketDailyDto.getApplyNuma()+"");
            row.createCell(9).setCellValue(marketDailyDto.getApplyNums()+"");
            row.createCell(10).setCellValue(marketDailyDto.getLoanAmount()+"");
            row.createCell(11).setCellValue(marketDailyDto.getLoanAmount().divide(new BigDecimal(marketDailyDto.getApplyNums()))+"");
            row.createCell(12).setCellValue(marketDailyDto.getIncomeA()+"");
            row.createCell(13).setCellValue(marketDailyDto.getApplyNuma()/marketDailyDto.getApplyNum()*100+"%");
            row.createCell(14).setCellValue(marketDailyDto.getIncomeS()+"");
            row.createCell(15).setCellValue(marketDailyDto.getApplyNums()/marketDailyDto.getApplyNum()*100+"%");
            row.createCell(16).setCellValue(marketDailyDto.getTotalIncome()+"");
            row.createCell(17).setCellValue(marketDailyDto.getThousandIncome()+"");
            row.createCell(18).setCellValue(marketDailyDto.getThousandCost()+"");
            row.createCell(19).setCellValue(marketDailyDto.getImportDate()+"");
            rowNum++;
        }
    }


    /**
     * 收入月报
     * @param sheet
     * @param reportSearchDto
     */
    private void createIncomeMonthData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<MarketDailyDto> entities = marketDailyAppService.getMarketMonth(reportSearchDto);
        for (MarketDailyDto marketDailyDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(marketDailyDto.getId());//序号
            row.createCell(1).setCellValue(marketDailyDto.getEnterpriseName());//公司名称
            row.createCell(2).setCellValue(marketDailyDto.getProductName());//产品名称
            row.createCell(3).setCellValue(marketDailyDto.getCooperationModel());//合作类型
            row.createCell(4).setCellValue(marketDailyDto.getApplyTime());//申请次数
            row.createCell(5).setCellValue(marketDailyDto.getApplyNum());//申请用户数
            row.createCell(6).setCellValue(marketDailyDto.getPriceA()==null?"":marketDailyDto.getPriceA()+"");//CPA价格
            row.createCell(7).setCellValue(marketDailyDto.getPriceS()==null?"":marketDailyDto.getPriceS()+"");//CPS价格

            row.createCell(8).setCellValue(marketDailyDto.getApplyNuma()==null?"":marketDailyDto.getApplyNuma()+"");//CPA数
            row.createCell(9).setCellValue(marketDailyDto.getApplyNums()==null?"":marketDailyDto.getApplyNums()+"");//CPS数
            row.createCell(10).setCellValue(marketDailyDto.getLoanAmount()==null?"":marketDailyDto.getLoanAmount()+"");//下款额
            if(marketDailyDto.getApplyNums()==null){
                marketDailyDto.setApplyNums(0);
            }
            if(marketDailyDto.getLoanAmount()==null){
                marketDailyDto.setLoanAmount(BigDecimal.ZERO);
            }
            if(marketDailyDto.getApplyNums()==0) {
                row.createCell(11).setCellValue(0);
            }else {
                row.createCell(11).setCellValue(marketDailyDto.getLoanAmount().divide(new BigDecimal(marketDailyDto.getApplyNums()), 2) + "");//均单值
            }
            row.createCell(12).setCellValue(marketDailyDto.getIncomeA()==null?"":marketDailyDto.getIncomeA()+"");//CPA收入
            if(marketDailyDto.getApplyNuma()==null){
                marketDailyDto.setApplyNuma(0);
            }
            row.createCell(13).setCellValue(marketDailyDto.getApplyNuma()/marketDailyDto.getApplyNum()*100+"%");//CPA转化率
            row.createCell(14).setCellValue(marketDailyDto.getIncomeS()+"");//CPS收入
            row.createCell(15).setCellValue(marketDailyDto.getApplyNums()/marketDailyDto.getApplyNum()*100+"%");//	CPS转化率
            row.createCell(16).setCellValue(marketDailyDto.getInvoiceFee()+"");//开票税费
            row.createCell(17).setCellValue(marketDailyDto.getTotalIncome()+"");//	总收入
            row.createCell(18).setCellValue(marketDailyDto.getIncomeFee()+"");//回款金额
            row.createCell(19).setCellValue(marketDailyDto.getIncomeMemo()==null?"":marketDailyDto.getIncomeMemo());//回款备注
            row.createCell(20).setCellValue(marketDailyDto.getImportDate()+"");//入库时间
            rowNum++;
        }
    }
    /**
     * 产品上线列表
     * @param sheet
     */
    private void createData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        ProductSearchDto dto = new ProductSearchDto();
        int rowNum = 1;
        List<ProductDto> entities = productAppService.findAll(dto);
        for (ProductDto productListDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(productListDto.getId());
            row.createCell(1).setCellValue(productListDto.getChannel().toString());
            row.createCell(2).setCellValue(productListDto.getName());
            row.createCell(3).setCellValue(productListDto.getViewNum());
            row.createCell(4).setCellValue(productListDto.getClickNum());
            row.createCell(5).setCellValue(productListDto.getCooperationModel());
            row.createCell(6).setCellValue(productListDto.getUnitPrice());
            row.createCell(7).setCellValue(productListDto.getIsValid()?"销售中":"已下架");
            rowNum++;
        }
    }
    /**
     * 应用市场数据
     * @param sheet
     */
    private void createAppMarketData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;

        List<HomeDto> entities = homeAppService.dayDev(reportSearchDto);
        for (HomeDto homeDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(homeDto.getId());
            row.createCell(1).setCellValue(homeDto.getChannelNo());
            row.createCell(2).setCellValue(homeDto.getRegisterNum());
            row.createCell(3).setCellValue(homeDto.getApplyTime());
            row.createCell(4).setCellValue(homeDto.getApplyNum());
            rowNum++;
        }
    }

    /**
     * 渠道数据
     * @param sheet
     * @param reportSearchDto
     */
    private void createChannelData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<HomeDto> entities = homeAppService.channelInOrOutcomeTotal(reportSearchDto);
        for (HomeDto homeDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(homeDto.getId());
            row.createCell(1).setCellValue(homeDto.getChannelNo());
            row.createCell(2).setCellValue(homeDto.getChannelCode());
            row.createCell(3).setCellValue(homeDto.getRegisterNum());
            row.createCell(4).setCellValue(homeDto.getApplyTime());
            row.createCell(5).setCellValue(homeDto.getApplyNum());
            rowNum++;
        }
    }
    /**
     * 渠道数据
     * @param sheet
     */
    private void createOperateData(HSSFWorkbook workbook,HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 3;
        List<OperateDto> entities = homeAppService.operateReport(reportSearchDto);
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        for (OperateDto operateDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(operateDto.getDay());
            row.createCell(1).setCellValue(operateDto.getMbdios()+operateDto.getMbdandroid()+operateDto.getMbdh5());
            row.createCell(2).setCellValue(operateDto.getMbdios());
            row.createCell(3).setCellValue(operateDto.getMbdandroid());
            row.createCell(4).setCellValue(operateDto.getMbdh5());
            row.createCell(5).setCellValue(operateDto.getMbdApplyNum()==null?"0":operateDto.getMbdApplyNum()+"");
            row.createCell(6).setCellValue(operateDto.getMbdApplyTime());
            if(operateDto.getMbdApplyNum()!=0) {
                row.createCell(7).setCellValue(Math.round(operateDto.getMbdApplyTime() / operateDto.getMbdApplyNum()));
            }else{
                row.createCell(7).setCellValue(0);
            }

            row.createCell(8).setCellValue(operateDto.getJqwios()+operateDto.getJqwandroid()+operateDto.getJqwh5());
            row.createCell(9).setCellValue(operateDto.getJqwios());
            row.createCell(10).setCellValue(operateDto.getJqwandroid());
            row.createCell(11).setCellValue(operateDto.getJqwh5());
            row.createCell(12).setCellValue(operateDto.getJqwApplyNum()==null?"0":operateDto.getJqwApplyNum()+"");
            row.createCell(13).setCellValue(operateDto.getJqwApplyTime());
            if(operateDto.getJqwApplyNum()!=0) {
                row.createCell(14).setCellValue(Math.round(operateDto.getJqwApplyTime() / operateDto.getJqwApplyNum()));
            }else{
                row.createCell(14).setCellValue(0);
            }

            row.createCell(15).setCellValue(operateDto.getXsdios()+operateDto.getXsdandroid()+operateDto.getXsdh5());
            row.createCell(16).setCellValue(operateDto.getXsdios());
            row.createCell(17).setCellValue(operateDto.getXsdandroid());
            row.createCell(18).setCellValue(operateDto.getXsdh5());
            row.createCell(19).setCellValue(operateDto.getXsdApplyNum()==null?"0":operateDto.getXsdApplyNum()+"");
            row.createCell(20).setCellValue(operateDto.getXsdApplyTime());
            if(operateDto.getXsdApplyNum()!=0) {
                row.createCell(21).setCellValue(Math.round(operateDto.getXsdApplyTime() / operateDto.getXsdApplyNum()));
            }else{
                row.createCell(21).setCellValue(0);
            }

            row.createCell(22).setCellValue(operateDto.getJdsios()+operateDto.getJdsandroid()+operateDto.getJdsh5());
            row.createCell(23).setCellValue(operateDto.getJdsios());
            row.createCell(24).setCellValue(operateDto.getJdsandroid());
            row.createCell(25).setCellValue(operateDto.getJdsh5());
            row.createCell(26).setCellValue(operateDto.getJdsApplyNum()==null?"0":operateDto.getJdsApplyNum()+"");
            row.createCell(27).setCellValue(operateDto.getJdsApplyTime());
            if(operateDto.getJdsApplyNum()!=0) {
                row.createCell(28).setCellValue(Math.round(operateDto.getJdsApplyTime() / operateDto.getJdsApplyNum()));
            }else{
                row.createCell(28).setCellValue(0);
            }

            row.createCell(29).setCellValue(operateDto.getDsqbios()+operateDto.getDsqbandroid()+operateDto.getDsqbh5());
            row.createCell(30).setCellValue(operateDto.getDsqbios());
            row.createCell(31).setCellValue(operateDto.getDsqbandroid());
            row.createCell(32).setCellValue(operateDto.getDsqbh5());
            row.createCell(33).setCellValue(operateDto.getDsqbApplyNum()==null?"0":operateDto.getDsqbApplyNum()+"");
            row.createCell(34).setCellValue(operateDto.getDsqbApplyTime());
            if(operateDto.getDsqbApplyNum()!=0) {
                row.createCell(35).setCellValue(Math.round(operateDto.getDsqbApplyTime() / operateDto.getDsqbApplyNum()));
            }else{
                row.createCell(35).setCellValue(0);
            }

            row.createCell(36).setCellValue(operateDto.getDkdios()+operateDto.getDkdandroid()+operateDto.getDkdh5());
            row.createCell(37).setCellValue(operateDto.getDkdios());
            row.createCell(38).setCellValue(operateDto.getDkdandroid());
            row.createCell(39).setCellValue(operateDto.getDkdh5());
            row.createCell(40).setCellValue(operateDto.getDkdApplyNum()==null?"0":operateDto.getDkdApplyNum()+"");
            row.createCell(41).setCellValue(operateDto.getDkdApplyTime());
            if(operateDto.getDkdApplyNum()!=0) {
                row.createCell(42).setCellValue(Math.round(operateDto.getDkdApplyTime() / operateDto.getDkdApplyNum()));
            }else{
                row.createCell(42).setCellValue(0);
            }

            row.createCell(43).setCellValue(operateDto.getDkqbios()+operateDto.getDkqbandroid()+operateDto.getDkqbh5());
            row.createCell(44).setCellValue(operateDto.getDkqbios());
            row.createCell(45).setCellValue(operateDto.getDkqbandroid());
            row.createCell(46).setCellValue(operateDto.getDkqbh5());
            row.createCell(47).setCellValue(operateDto.getDkqbApplyNum()==null?"0":operateDto.getDkqbApplyNum()+"");
            row.createCell(48).setCellValue(operateDto.getDkqbApplyTime());
            if(operateDto.getDkqbApplyNum()!=0) {
                row.createCell(49).setCellValue(Math.round(operateDto.getDkqbApplyTime() / operateDto.getDkqbApplyNum()));
            }else{
                row.createCell(49).setCellValue(0);
            }

            row.createCell(50).setCellValue(operateDto.getPxqbios()+operateDto.getPxqbandroid()+operateDto.getPxqbh5());
            row.createCell(51).setCellValue(operateDto.getPxqbios());
            row.createCell(52).setCellValue(operateDto.getPxqbandroid());
            row.createCell(53).setCellValue(operateDto.getPxqbh5());
            row.createCell(54).setCellValue(operateDto.getPxqbApplyNum()==null?"0":operateDto.getPxqbApplyNum()+"");
            row.createCell(55).setCellValue(operateDto.getPxqbApplyTime());
            if(operateDto.getPxqbApplyNum()!=0) {
                row.createCell(56).setCellValue(Math.round(operateDto.getPxqbApplyTime() / operateDto.getPxqbApplyNum()));
            }else{
                row.createCell(56).setCellValue(0);
            }

            row.createCell(57).setCellValue(operateDto.getDkblios()+operateDto.getDkblandroid()+operateDto.getDkblh5());
            row.createCell(58).setCellValue(operateDto.getDkblios());
            row.createCell(59).setCellValue(operateDto.getDkblandroid());
            row.createCell(60).setCellValue(operateDto.getDkblh5());
            row.createCell(61).setCellValue(operateDto.getDkblApplyNum()==null?"0":operateDto.getDkblApplyNum()+"");
            row.createCell(62).setCellValue(operateDto.getDkblApplyTime());
            if(operateDto.getDkblApplyNum()!=0) {
                row.createCell(63).setCellValue(Math.round(operateDto.getDkblApplyTime() / operateDto.getDkblApplyNum()));
            }else{
                row.createCell(63).setCellValue(0);
            }

            row.createCell(64).setCellValue(operateDto.getLsqdios()+operateDto.getLsqdandroid()+operateDto.getLsqdh5());
            row.createCell(65).setCellValue(operateDto.getLsqdios());
            row.createCell(66).setCellValue(operateDto.getLsqdandroid());
            row.createCell(67).setCellValue(operateDto.getLsqdh5());
            row.createCell(68).setCellValue(operateDto.getLsqdApplyNum()==null?"0":operateDto.getLsqdApplyNum()+"");
            row.createCell(69).setCellValue(operateDto.getLsqdApplyTime());
            if(operateDto.getLsqdApplyNum()!=0) {
                row.createCell(70).setCellValue(Math.round(operateDto.getLsqdApplyTime() / operateDto.getLsqdApplyNum()));
            }else{
                row.createCell(70).setCellValue(0);
            }

            row.createCell(71).setCellValue(operateDto.getSydios()+operateDto.getSydandroid()+operateDto.getSydh5());
            row.createCell(72).setCellValue(operateDto.getSydios());
            row.createCell(73).setCellValue(operateDto.getSydandroid());
            row.createCell(74).setCellValue(operateDto.getSydh5());
            row.createCell(75).setCellValue(operateDto.getSydApplyNum()==null?"0":operateDto.getSydApplyNum()+"");
            row.createCell(76).setCellValue(operateDto.getSydApplyTime());
            if(operateDto.getDqlApplyNum()!=0) {
                row.createCell(77).setCellValue(Math.round(operateDto.getSydApplyTime() / operateDto.getSydApplyNum()));
            }else{
                row.createCell(77).setCellValue(0);
            }

            row.createCell(78).setCellValue(operateDto.getSrdios()+operateDto.getSrdandroid()+operateDto.getSrdh5());
            row.createCell(79).setCellValue(operateDto.getSrdios());
            row.createCell(80).setCellValue(operateDto.getSrdandroid());
            row.createCell(81).setCellValue(operateDto.getSrdh5());
            row.createCell(82).setCellValue(operateDto.getSrdApplyNum()==null?"0":operateDto.getSrdApplyNum()+"");
            row.createCell(83).setCellValue(operateDto.getSrdApplyTime());
            if(operateDto.getJdbApplyNum()!=0) {
                row.createCell(84).setCellValue(Math.round(operateDto.getSrdApplyTime() / operateDto.getSrdApplyNum()));
            }else{
                row.createCell(84).setCellValue(0);
            }

            row.createCell(85).setCellValue(operateDto.getDkzgios()+operateDto.getDkzgandroid()+operateDto.getDkzgh5());
            row.createCell(86).setCellValue(operateDto.getDkzgios());
            row.createCell(87).setCellValue(operateDto.getDkzgandroid());
            row.createCell(88).setCellValue(operateDto.getDkzgh5());
            row.createCell(89).setCellValue(operateDto.getDkzgApplyNum()==null?"0":operateDto.getDkzgApplyNum()+"");
            row.createCell(90).setCellValue(operateDto.getDkzgApplyTime());
            if(operateDto.getDkzgApplyNum()!=0) {
                row.createCell(91).setCellValue(Math.round(operateDto.getDkzgApplyTime() / operateDto.getDkzgApplyNum()));
            }else{
                row.createCell(91).setCellValue(0);
            }

            row.createCell(92).setCellValue(operateDto.getJjkios()+operateDto.getJjkandroid()+operateDto.getJjkh5());
            row.createCell(93).setCellValue(operateDto.getJjkios());
            row.createCell(94).setCellValue(operateDto.getJjkandroid());
            row.createCell(95).setCellValue(operateDto.getJjkh5());
            row.createCell(96).setCellValue(operateDto.getJjkApplyNum()==null?"0":operateDto.getJjkApplyNum()+"");
            row.createCell(97).setCellValue(operateDto.getJjkApplyTime());
            if(operateDto.getJjkApplyNum()!=0) {
                row.createCell(98).setCellValue(Math.round(operateDto.getJjkApplyTime() / operateDto.getJjkApplyNum()));
            }else{
                row.createCell(98).setCellValue(0);
            }

            row.createCell(99).setCellValue(operateDto.getDqlios()+operateDto.getDqlandroid()+operateDto.getDqlh5());
            row.createCell(100).setCellValue(operateDto.getDqlios());
            row.createCell(101).setCellValue(operateDto.getDqlandroid());
            row.createCell(102).setCellValue(operateDto.getDqlh5());
            row.createCell(103).setCellValue(operateDto.getDqlApplyNum()==null?"0":operateDto.getDqlApplyNum()+"");
            row.createCell(104).setCellValue(operateDto.getApplyTime());
            if(operateDto.getDqlApplyNum()!=0) {
                row.createCell(105).setCellValue(Math.round(operateDto.getDqlApplyTime() / operateDto.getDqlApplyNum()));
            }else{
                row.createCell(105).setCellValue(0);
            }

            row.createCell(106).setCellValue(operateDto.getJdbios()+operateDto.getJdbandroid()+operateDto.getJdbh5());
            row.createCell(107).setCellValue(operateDto.getJdbios());
            row.createCell(108).setCellValue(operateDto.getJdbandroid());
            row.createCell(109).setCellValue(operateDto.getJdbh5());
            row.createCell(110).setCellValue(operateDto.getJdbApplyNum()==null?"0":operateDto.getJdbApplyNum()+"");
            row.createCell(111).setCellValue(operateDto.getJdbApplyTime());
            if(operateDto.getJdbApplyNum()!=0) {
                row.createCell(112).setCellValue(Math.round(operateDto.getJdbApplyTime() / operateDto.getJdbApplyNum()));
            }else{
                row.createCell(112).setCellValue(0);
            }

            row.createCell(113).setCellValue(operateDto.getJqhhios()+operateDto.getJqhhandroid()+operateDto.getJqhhh5());
            row.createCell(114).setCellValue(operateDto.getJqhhios());
            row.createCell(115).setCellValue(operateDto.getJqhhandroid());
            row.createCell(116).setCellValue(operateDto.getJqhhh5());
            row.createCell(117).setCellValue(operateDto.getJqhhApplyNum()==null?"0":operateDto.getJqhhApplyNum()+"");
            row.createCell(118).setCellValue(operateDto.getJqhhApplyTime());
            if(operateDto.getJqhhApplyNum()!=0) {
                row.createCell(119).setCellValue(Math.round(operateDto.getJqhhApplyTime() / operateDto.getJqhhApplyNum()));
            }else{
                row.createCell(119).setCellValue(0);
            }

            row.createCell(120).setCellValue(operateDto.getKjwios()+operateDto.getKjwandroid()+operateDto.getKjwh5());
            row.createCell(121).setCellValue(operateDto.getKjwios());
            row.createCell(122).setCellValue(operateDto.getKjwandroid());
            row.createCell(123).setCellValue(operateDto.getKjwh5());
            row.createCell(124).setCellValue(operateDto.getKjwApplyNum()==null?"0":operateDto.getKjwApplyNum()+"");
            row.createCell(125).setCellValue(operateDto.getKjwApplyTime());
            if(operateDto.getKjwApplyNum()!=0) {
                row.createCell(126).setCellValue(Math.round(operateDto.getKjwApplyTime() / operateDto.getKjwApplyNum()));
            }else{
                row.createCell(126).setCellValue(0);
            }

            row.createCell(127).setCellValue(operateDto.getDkssios()+operateDto.getDkssandroid()+operateDto.getDkssh5());
            row.createCell(128).setCellValue(operateDto.getDkssios());
            row.createCell(129).setCellValue(operateDto.getDkssandroid());
            row.createCell(130).setCellValue(operateDto.getDkssh5());
            row.createCell(131).setCellValue(operateDto.getDkssApplyNum()==null?"0":operateDto.getDkssApplyNum()+"");
            row.createCell(132).setCellValue(operateDto.getDkssApplyTime());
            if(operateDto.getDkssApplyNum()!=0) {
                row.createCell(133).setCellValue(Math.round(operateDto.getDkssApplyTime() / operateDto.getDkssApplyNum()));
            }else{
                row.createCell(133).setCellValue(0);
            }

            row.createCell(134).setCellValue(operateDto.getJqfios()+operateDto.getJqfandroid()+operateDto.getJqfh5());
            row.createCell(135).setCellValue(operateDto.getJqfios());
            row.createCell(136).setCellValue(operateDto.getJqfandroid());
            row.createCell(137).setCellValue(operateDto.getJqfh5());
            row.createCell(138).setCellValue(operateDto.getJqfApplyNum()==null?"0":operateDto.getJqfApplyNum()+"");
            row.createCell(139).setCellValue(operateDto.getJqfApplyTime());
            if(operateDto.getJqfApplyNum()!=0) {
                row.createCell(140).setCellValue(Math.round(operateDto.getJqfApplyTime() / operateDto.getJqfApplyNum()));
            }else{
                row.createCell(140).setCellValue(0);
            }

            row.createCell(141).setCellValue(operateDto.getDkhios()+operateDto.getDkhandroid()+operateDto.getDkhh5());
            row.createCell(142).setCellValue(operateDto.getDkhios());
            row.createCell(143).setCellValue(operateDto.getDkhandroid());
            row.createCell(144).setCellValue(operateDto.getDkhh5());
            row.createCell(145).setCellValue(operateDto.getDkhApplyNum()==null?"0":operateDto.getDkhApplyNum()+"");
            row.createCell(146).setCellValue(operateDto.getDkhApplyTime());
            if(operateDto.getDkhApplyNum()!=0) {
                row.createCell(147).setCellValue(Math.round(operateDto.getDkhApplyTime() / operateDto.getDkhApplyNum()));
            }else{
                row.createCell(147).setCellValue(0);
            }

            row.createCell(148).setCellValue(operateDto.getMdbios()+operateDto.getMdbandroid()+operateDto.getMdbh5());
            row.createCell(149).setCellValue(operateDto.getMdbios());
            row.createCell(150).setCellValue(operateDto.getMdbandroid());
            row.createCell(151).setCellValue(operateDto.getMdbh5());
            row.createCell(152).setCellValue(operateDto.getMdbApplyNum()==null?"0":operateDto.getMdbApplyNum()+"");
            row.createCell(153).setCellValue(operateDto.getMdbApplyTime());
            if(operateDto.getMdbApplyNum()!=0) {
                row.createCell(154).setCellValue(Math.round(operateDto.getMdbApplyTime() / operateDto.getMdbApplyNum()));
            }else{
                row.createCell(154).setCellValue(0);
            }

            row.createCell(155).setCellValue(operateDto.getXedios()+operateDto.getXedandroid()+operateDto.getXedh5());
            row.createCell(156).setCellValue(operateDto.getXedios());
            row.createCell(157).setCellValue(operateDto.getXedandroid());
            row.createCell(158).setCellValue(operateDto.getXedh5());
            row.createCell(159).setCellValue(operateDto.getXedApplyNum()==null?"0":operateDto.getXedApplyNum()+"");
            row.createCell(160).setCellValue(operateDto.getXedApplyTime());
            if(operateDto.getXedApplyNum()!=0) {
                row.createCell(161).setCellValue(Math.round(operateDto.getXedApplyTime() / operateDto.getXedApplyNum()));
            }else{
                row.createCell(161).setCellValue(0);
            }

            row.createCell(162).setCellValue(operateDto.getJqsios()+operateDto.getJqsandroid()+operateDto.getJqsh5());
            row.createCell(163).setCellValue(operateDto.getJqsios());
            row.createCell(164).setCellValue(operateDto.getJqsandroid());
            row.createCell(165).setCellValue(operateDto.getJqsh5());
            row.createCell(166).setCellValue(operateDto.getJqsApplyNum()==null?"0":operateDto.getJqsApplyNum()+"");
            row.createCell(167).setCellValue(operateDto.getJqsApplyTime());
            if(operateDto.getJqsApplyNum()!=0) {
                row.createCell(168).setCellValue(Math.round(operateDto.getJqsApplyTime() / operateDto.getJqsApplyNum()));
            }else{
                row.createCell(168).setCellValue(0);
            }

            row.createCell(169).setCellValue(operateDto.getJdjqios()+operateDto.getJdjqandroid()+operateDto.getJdjqh5());
            row.createCell(170).setCellValue(operateDto.getJdjqios());
            row.createCell(171).setCellValue(operateDto.getJdjqandroid());
            row.createCell(172).setCellValue(operateDto.getJdjqh5());
            row.createCell(173).setCellValue(operateDto.getJdjqApplyNum()==null?"0":operateDto.getJdjqApplyNum()+"");
            row.createCell(174).setCellValue(operateDto.getJdjqApplyTime());
            if(operateDto.getJdjqApplyNum()!=0) {
                row.createCell(175).setCellValue(Math.round(operateDto.getJdjqApplyTime() / operateDto.getJdjqApplyNum()));
            }else{
                row.createCell(175).setCellValue(0);
            }

            row.createCell(176).setCellValue(operateDto.getDktios()+operateDto.getDktandroid()+operateDto.getDkth5());
            row.createCell(177).setCellValue(operateDto.getDktios());
            row.createCell(178).setCellValue(operateDto.getDktandroid());
            row.createCell(179).setCellValue(operateDto.getDkth5());
            row.createCell(180).setCellValue(operateDto.getDktApplyNum()==null?"0":operateDto.getDktApplyNum()+"");
            row.createCell(181).setCellValue(operateDto.getDktApplyTime());
            if(operateDto.getDktApplyNum()!=0) {
                row.createCell(182).setCellValue(Math.round(operateDto.getDktApplyTime() / operateDto.getDktApplyNum()));
            }else{
                row.createCell(182).setCellValue(0);
            }

            row.createCell(183).setCellValue(operateDto.getHdbios()+operateDto.getHdbandroid()+operateDto.getHdbh5());
            row.createCell(184).setCellValue(operateDto.getHdbios());
            row.createCell(185).setCellValue(operateDto.getHdbandroid());
            row.createCell(186).setCellValue(operateDto.getHdbh5());
            row.createCell(187).setCellValue(operateDto.getHdbApplyNum()==null?"0":operateDto.getHdbApplyNum()+"");
            row.createCell(188).setCellValue(operateDto.getHdbApplyTime());
            if(operateDto.getHdbApplyNum()!=0) {
                row.createCell(189).setCellValue(Math.round(operateDto.getHdbApplyTime() / operateDto.getHdbApplyNum()));
            }else{
                row.createCell(189).setCellValue(0);
            }

            row.createCell(190).setCellValue(operateDto.getYjxios()+operateDto.getYjxandroid()+operateDto.getYjxh5());
            row.createCell(191).setCellValue(operateDto.getYjxios());
            row.createCell(192).setCellValue(operateDto.getYjxandroid());
            row.createCell(193).setCellValue(operateDto.getYjxh5());
            row.createCell(194).setCellValue(operateDto.getYjxApplyNum()==null?"0":operateDto.getYjxApplyNum()+"");
            row.createCell(195).setCellValue(operateDto.getYjxApplyTime());
            if(operateDto.getYjxApplyNum()!=0) {
                row.createCell(196).setCellValue(Math.round(operateDto.getYjxApplyTime() / operateDto.getYjxApplyNum()));
            }else{
                row.createCell(196).setCellValue(0);
            }

            row.createCell(197).setCellValue(operateDto.getIos()+operateDto.getAndroid()+operateDto.getH5());
            row.createCell(198).setCellValue(operateDto.getIos());
            row.createCell(199).setCellValue(operateDto.getAndroid());
            row.createCell(200).setCellValue(operateDto.getH5());
            row.createCell(201).setCellValue(operateDto.getApplyNum()==null?"0":operateDto.getApplyNum()+"");
            row.createCell(202).setCellValue(operateDto.getApplyTime());
            if(operateDto.getApplyNum()!=0) {
                row.createCell(203).setCellValue(Math.round(operateDto.getApplyTime() / operateDto.getApplyNum()));
            }else{
                row.createCell(203).setCellValue(0);
            }

            for(int k=0;k<204;k++) {
                row.getCell(k).setCellStyle(style);
            }

            rowNum++;
        }
    }

    /**
     * 商户来量明细
     * @param sheet
     * @param reportSearchDto
     */
    private void createDetailData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<HomeDto> entities = homeAppService.merchantIncome(reportSearchDto);
        for (HomeDto homeDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(homeDto.getId());
            row.createCell(1).setCellValue(homeDto.getMerchantName());
            row.createCell(2).setCellValue(homeDto.getChannelNo());
            row.createCell(3).setCellValue(homeDto.getChannelCode());
            row.createCell(4).setCellValue(homeDto.getAppName());
            row.createCell(5).setCellValue(homeDto.getApplyTime());
            row.createCell(6).setCellValue(homeDto.getApplyNum());
            rowNum++;
        }
    }

    /**
     * 商户来量统计
     * @param sheet
     * @param reportSearchDto
     */
    private void createTotalData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<HomeDto> entities = homeAppService.monthDev(reportSearchDto);
        for (HomeDto homeDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(homeDto.getId());
            row.createCell(1).setCellValue(homeDto.getChannelNo());
            row.createCell(2).setCellValue(homeDto.getMerchantName());
            row.createCell(3).setCellValue(homeDto.getApplyTime());
            row.createCell(4).setCellValue(homeDto.getApplyNum());
            rowNum++;
        }
    }

    /**
     * APP用户分布
     * @param sheet
     * @param reportSearchDto
     */
    private void createAppUserData(HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        List<AppDevDto> entities = homeAppService.appDevUser(reportSearchDto);
        for (AppDevDto homeDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(homeDto.getId());
            row.createCell(1).setCellValue(homeDto.getName());
            row.createCell(2).setCellValue(homeDto.getDevUser());
            row.createCell(3).setCellValue(homeDto.getApplyTime());
            row.createCell(4).setCellValue(homeDto.getApplyNum());
            rowNum++;
        }
    }

    /**
     * 应用市场汇总数据
     * @param sheet
     * @param reportSearchDto
     */
    private void createAppTotalData(HSSFWorkbook workbook,HSSFSheet sheet,ReportSearchDto reportSearchDto){
        //新增数据行，并且设置单元格数据
        int rowNum = 2;
        List<AppMarketDto> entities = homeAppService.appMarketReport(reportSearchDto);
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        for (AppMarketDto appMarketDto:entities) {
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(appMarketDto.getDay());
            row.createCell(1).setCellValue(appMarketDto.getMbdHuawei());
            row.createCell(2).setCellValue(appMarketDto.getMbdXiaomi());
            row.createCell(3).setCellValue(appMarketDto.getMbdVivo());
            row.createCell(4).setCellValue(appMarketDto.getMbdOppo());
            row.createCell(5).setCellValue(appMarketDto.getMbd360());
            row.createCell(6).setCellValue(appMarketDto.getMbdYyb());
            row.createCell(7).setCellValue(appMarketDto.getMbdApple());
            row.createCell(8).setCellValue(appMarketDto.getMbdMeizu());
            row.createCell(9).setCellValue(appMarketDto.getMbdWandoujia());
            row.createCell(10).setCellValue(appMarketDto.getMbdBaidu());
            row.createCell(11).setCellValue(appMarketDto.getMbdOther());

            row.createCell(12).setCellValue(appMarketDto.getJqwHuawei());
            row.createCell(13).setCellValue(appMarketDto.getJqwXiaomi());
            row.createCell(14).setCellValue(appMarketDto.getJqwVivo());
            row.createCell(15).setCellValue(appMarketDto.getJqwOppo());
            row.createCell(16).setCellValue(appMarketDto.getJqw360());
            row.createCell(17).setCellValue(appMarketDto.getJqwYyb());
            row.createCell(18).setCellValue(appMarketDto.getJqwApple());
            row.createCell(19).setCellValue(appMarketDto.getJqwMeizu());
            row.createCell(20).setCellValue(appMarketDto.getJqwWandoujia());
            row.createCell(21).setCellValue(appMarketDto.getJqwBaidu());
            row.createCell(22).setCellValue(appMarketDto.getJqwOther());

            row.createCell(23).setCellValue(appMarketDto.getXsdHuawei());
            row.createCell(24).setCellValue(appMarketDto.getXsdXiaomi());
            row.createCell(25).setCellValue(appMarketDto.getXsdVivo());
            row.createCell(26).setCellValue(appMarketDto.getXsdOppo());
            row.createCell(27).setCellValue(appMarketDto.getXsd360());
            row.createCell(28).setCellValue(appMarketDto.getXsdYyb());
            row.createCell(29).setCellValue(appMarketDto.getXsdApple());
            row.createCell(30).setCellValue(appMarketDto.getXsdMeizu());
            row.createCell(31).setCellValue(appMarketDto.getXsdWandoujia());
            row.createCell(32).setCellValue(appMarketDto.getXsdBaidu());
            row.createCell(33).setCellValue(appMarketDto.getXsdOther());

            row.createCell(34).setCellValue(appMarketDto.getJdsHuawei());
            row.createCell(35).setCellValue(appMarketDto.getJdsXiaomi());
            row.createCell(36).setCellValue(appMarketDto.getJdsVivo());
            row.createCell(37).setCellValue(appMarketDto.getJdsOppo());
            row.createCell(38).setCellValue(appMarketDto.getJds360());
            row.createCell(39).setCellValue(appMarketDto.getJdsYyb());
            row.createCell(40).setCellValue(appMarketDto.getJdsApple());
            row.createCell(41).setCellValue(appMarketDto.getJdsMeizu());
            row.createCell(42).setCellValue(appMarketDto.getJdsWandoujia());
            row.createCell(43).setCellValue(appMarketDto.getJdsBaidu());
            row.createCell(44).setCellValue(appMarketDto.getJdsOther());

            row.createCell(45).setCellValue(appMarketDto.getDsqbHuawei());
            row.createCell(46).setCellValue(appMarketDto.getDsqbXiaomi());
            row.createCell(47).setCellValue(appMarketDto.getDsqbVivo());
            row.createCell(48).setCellValue(appMarketDto.getDsqbOppo());
            row.createCell(49).setCellValue(appMarketDto.getDsqb360());
            row.createCell(50).setCellValue(appMarketDto.getDsqbYyb());
            row.createCell(51).setCellValue(appMarketDto.getDsqbApple());
            row.createCell(52).setCellValue(appMarketDto.getDsqbMeizu());
            row.createCell(53).setCellValue(appMarketDto.getDsqbWandoujia());
            row.createCell(54).setCellValue(appMarketDto.getDsqbBaidu());
            row.createCell(55).setCellValue(appMarketDto.getDsqbOther());

            row.createCell(56).setCellValue(appMarketDto.getDkdHuawei());
            row.createCell(57).setCellValue(appMarketDto.getDkdXiaomi());
            row.createCell(58).setCellValue(appMarketDto.getDkdVivo());
            row.createCell(59).setCellValue(appMarketDto.getDkdOppo());
            row.createCell(60).setCellValue(appMarketDto.getDkd360());
            row.createCell(61).setCellValue(appMarketDto.getDkdYyb());
            row.createCell(62).setCellValue(appMarketDto.getDkdApple());
            row.createCell(63).setCellValue(appMarketDto.getDkdMeizu());
            row.createCell(64).setCellValue(appMarketDto.getDkdWandoujia());
            row.createCell(65).setCellValue(appMarketDto.getDkdBaidu());
            row.createCell(66).setCellValue(appMarketDto.getDkdOther());

            row.createCell(67).setCellValue(appMarketDto.getDkqbHuawei());
            row.createCell(68).setCellValue(appMarketDto.getDkqbXiaomi());
            row.createCell(69).setCellValue(appMarketDto.getDkqbVivo());
            row.createCell(70).setCellValue(appMarketDto.getDkqbOppo());
            row.createCell(71).setCellValue(appMarketDto.getDkqb360());
            row.createCell(72).setCellValue(appMarketDto.getDkqbYyb());
            row.createCell(73).setCellValue(appMarketDto.getDkqbApple());
            row.createCell(74).setCellValue(appMarketDto.getDkqbMeizu());
            row.createCell(75).setCellValue(appMarketDto.getDkqbWandoujia());
            row.createCell(76).setCellValue(appMarketDto.getDkqbBaidu());
            row.createCell(77).setCellValue(appMarketDto.getDkqbOther());

            row.createCell(78).setCellValue(appMarketDto.getPxqbHuawei());
            row.createCell(79).setCellValue(appMarketDto.getPxqbXiaomi());
            row.createCell(80).setCellValue(appMarketDto.getPxqbVivo());
            row.createCell(81).setCellValue(appMarketDto.getPxqbOppo());
            row.createCell(82).setCellValue(appMarketDto.getPxqb360());
            row.createCell(83).setCellValue(appMarketDto.getPxqbYyb());
            row.createCell(84).setCellValue(appMarketDto.getPxqbApple());
            row.createCell(85).setCellValue(appMarketDto.getPxqbMeizu());
            row.createCell(86).setCellValue(appMarketDto.getPxqbWandoujia());
            row.createCell(87).setCellValue(appMarketDto.getPxqbBaidu());
            row.createCell(88).setCellValue(appMarketDto.getPxqbOther());

            row.createCell(89).setCellValue(appMarketDto.getDkblHuawei());
            row.createCell(90).setCellValue(appMarketDto.getDkblXiaomi());
            row.createCell(91).setCellValue(appMarketDto.getDkblVivo());
            row.createCell(92).setCellValue(appMarketDto.getDkblOppo());
            row.createCell(93).setCellValue(appMarketDto.getDkbl360());
            row.createCell(94).setCellValue(appMarketDto.getDkblYyb());
            row.createCell(95).setCellValue(appMarketDto.getDkblApple());
            row.createCell(96).setCellValue(appMarketDto.getDkblMeizu());
            row.createCell(97).setCellValue(appMarketDto.getDkblWandoujia());
            row.createCell(98).setCellValue(appMarketDto.getDkblBaidu());
            row.createCell(99).setCellValue(appMarketDto.getDkblOther());

            row.createCell(100).setCellValue(appMarketDto.getLsqdHuawei());
            row.createCell(101).setCellValue(appMarketDto.getLsqdXiaomi());
            row.createCell(102).setCellValue(appMarketDto.getLsqdVivo());
            row.createCell(103).setCellValue(appMarketDto.getLsqdOppo());
            row.createCell(104).setCellValue(appMarketDto.getLsqd360());
            row.createCell(105).setCellValue(appMarketDto.getLsqdYyb());
            row.createCell(106).setCellValue(appMarketDto.getLsqdApple());
            row.createCell(107).setCellValue(appMarketDto.getLsqdMeizu());
            row.createCell(108).setCellValue(appMarketDto.getLsqdWandoujia());
            row.createCell(109).setCellValue(appMarketDto.getLsqdBaidu());
            row.createCell(110).setCellValue(appMarketDto.getLsqdOther());

            row.createCell(111).setCellValue(appMarketDto.getSydHuawei());
            row.createCell(112).setCellValue(appMarketDto.getSydXiaomi());
            row.createCell(113).setCellValue(appMarketDto.getSydVivo());
            row.createCell(114).setCellValue(appMarketDto.getSydOppo());
            row.createCell(115).setCellValue(appMarketDto.getSyd360());
            row.createCell(116).setCellValue(appMarketDto.getSydYyb());
            row.createCell(117).setCellValue(appMarketDto.getSydApple());
            row.createCell(118).setCellValue(appMarketDto.getSydMeizu());
            row.createCell(119).setCellValue(appMarketDto.getSydWandoujia());
            row.createCell(120).setCellValue(appMarketDto.getSydBaidu());
            row.createCell(121).setCellValue(appMarketDto.getSydOther());

            row.createCell(122).setCellValue(appMarketDto.getSrdHuawei());
            row.createCell(123).setCellValue(appMarketDto.getSrdXiaomi());
            row.createCell(124).setCellValue(appMarketDto.getSrdVivo());
            row.createCell(125).setCellValue(appMarketDto.getSrdOppo());
            row.createCell(126).setCellValue(appMarketDto.getSrd360());
            row.createCell(127).setCellValue(appMarketDto.getSrdYyb());
            row.createCell(128).setCellValue(appMarketDto.getSrdApple());
            row.createCell(129).setCellValue(appMarketDto.getSrdMeizu());
            row.createCell(130).setCellValue(appMarketDto.getSrdWandoujia());
            row.createCell(131).setCellValue(appMarketDto.getSrdBaidu());
            row.createCell(132).setCellValue(appMarketDto.getSrdOther());

            row.createCell(133).setCellValue(appMarketDto.getDqlHuawei());
            row.createCell(134).setCellValue(appMarketDto.getDqlXiaomi());
            row.createCell(135).setCellValue(appMarketDto.getDqlVivo());
            row.createCell(136).setCellValue(appMarketDto.getDqlOppo());
            row.createCell(137).setCellValue(appMarketDto.getDql360());
            row.createCell(138).setCellValue(appMarketDto.getDqlYyb());
            row.createCell(139).setCellValue(appMarketDto.getDqlApple());
            row.createCell(140).setCellValue(appMarketDto.getDqlMeizu());
            row.createCell(141).setCellValue(appMarketDto.getDqlWandoujia());
            row.createCell(142).setCellValue(appMarketDto.getDqlBaidu());
            row.createCell(143).setCellValue(appMarketDto.getDqlOther());

            row.createCell(144).setCellValue(appMarketDto.getJdbHuawei());
            row.createCell(145).setCellValue(appMarketDto.getJdbXiaomi());
            row.createCell(146).setCellValue(appMarketDto.getJdbVivo());
            row.createCell(147).setCellValue(appMarketDto.getJdbOppo());
            row.createCell(148).setCellValue(appMarketDto.getJdb360());
            row.createCell(149).setCellValue(appMarketDto.getJdbYyb());
            row.createCell(150).setCellValue(appMarketDto.getJdbApple());
            row.createCell(151).setCellValue(appMarketDto.getJdbMeizu());
            row.createCell(152).setCellValue(appMarketDto.getJdbWandoujia());
            row.createCell(153).setCellValue(appMarketDto.getJdbBaidu());
            row.createCell(154).setCellValue(appMarketDto.getJdbOther());

            row.createCell(155).setCellValue(appMarketDto.getDkzgHuawei());
            row.createCell(156).setCellValue(appMarketDto.getDkzgXiaomi());
            row.createCell(157).setCellValue(appMarketDto.getDkzgVivo());
            row.createCell(158).setCellValue(appMarketDto.getDkzgOppo());
            row.createCell(159).setCellValue(appMarketDto.getDkzg360());
            row.createCell(160).setCellValue(appMarketDto.getDkzgYyb());
            row.createCell(161).setCellValue(appMarketDto.getDkzgApple());
            row.createCell(162).setCellValue(appMarketDto.getDkzgMeizu());
            row.createCell(163).setCellValue(appMarketDto.getDkzgWandoujia());
            row.createCell(164).setCellValue(appMarketDto.getDkzgBaidu());
            row.createCell(165).setCellValue(appMarketDto.getDkzgOther());

            row.createCell(166).setCellValue(appMarketDto.getJjkHuawei());
            row.createCell(167).setCellValue(appMarketDto.getJjkXiaomi());
            row.createCell(168).setCellValue(appMarketDto.getJjkVivo());
            row.createCell(169).setCellValue(appMarketDto.getJjkOppo());
            row.createCell(170).setCellValue(appMarketDto.getJjk360());
            row.createCell(171).setCellValue(appMarketDto.getJjkYyb());
            row.createCell(172).setCellValue(appMarketDto.getJjkApple());
            row.createCell(173).setCellValue(appMarketDto.getJjkMeizu());
            row.createCell(174).setCellValue(appMarketDto.getJjkWandoujia());
            row.createCell(175).setCellValue(appMarketDto.getJjkBaidu());
            row.createCell(176).setCellValue(appMarketDto.getJjkOther());

            row.createCell(177).setCellValue(appMarketDto.getJqhhHuawei());
            row.createCell(178).setCellValue(appMarketDto.getJqhhXiaomi());
            row.createCell(179).setCellValue(appMarketDto.getJqhhVivo());
            row.createCell(180).setCellValue(appMarketDto.getJqhhOppo());
            row.createCell(181).setCellValue(appMarketDto.getJqhh360());
            row.createCell(182).setCellValue(appMarketDto.getJqhhYyb());
            row.createCell(183).setCellValue(appMarketDto.getJqhhApple());
            row.createCell(184).setCellValue(appMarketDto.getJqhhMeizu());
            row.createCell(185).setCellValue(appMarketDto.getJqhhWandoujia());
            row.createCell(186).setCellValue(appMarketDto.getJqhhBaidu());
            row.createCell(187).setCellValue(appMarketDto.getJqhhOther());

            row.createCell(188).setCellValue(appMarketDto.getKjwHuawei());
            row.createCell(189).setCellValue(appMarketDto.getKjwXiaomi());
            row.createCell(190).setCellValue(appMarketDto.getKjwVivo());
            row.createCell(191).setCellValue(appMarketDto.getKjwOppo());
            row.createCell(192).setCellValue(appMarketDto.getKjw360());
            row.createCell(193).setCellValue(appMarketDto.getKjwYyb());
            row.createCell(194).setCellValue(appMarketDto.getKjwApple());
            row.createCell(195).setCellValue(appMarketDto.getKjwMeizu());
            row.createCell(196).setCellValue(appMarketDto.getKjwWandoujia());
            row.createCell(197).setCellValue(appMarketDto.getKjwBaidu());
            row.createCell(198).setCellValue(appMarketDto.getKjwOther());

            row.createCell(199).setCellValue(appMarketDto.getDkssHuawei());
            row.createCell(200).setCellValue(appMarketDto.getDkssXiaomi());
            row.createCell(201).setCellValue(appMarketDto.getDkssVivo());
            row.createCell(202).setCellValue(appMarketDto.getDkssOppo());
            row.createCell(203).setCellValue(appMarketDto.getDkss360());
            row.createCell(204).setCellValue(appMarketDto.getDkssYyb());
            row.createCell(205).setCellValue(appMarketDto.getDkssApple());
            row.createCell(206).setCellValue(appMarketDto.getDkssMeizu());
            row.createCell(207).setCellValue(appMarketDto.getDkssWandoujia());
            row.createCell(208).setCellValue(appMarketDto.getDkssBaidu());
            row.createCell(209).setCellValue(appMarketDto.getDkssOther());

            row.createCell(210).setCellValue(appMarketDto.getJqfHuawei());
            row.createCell(211).setCellValue(appMarketDto.getJqfXiaomi());
            row.createCell(212).setCellValue(appMarketDto.getJqfVivo());
            row.createCell(213).setCellValue(appMarketDto.getJqfOppo());
            row.createCell(214).setCellValue(appMarketDto.getJqf360());
            row.createCell(215).setCellValue(appMarketDto.getJqfYyb());
            row.createCell(216).setCellValue(appMarketDto.getJqfApple());
            row.createCell(217).setCellValue(appMarketDto.getJqfMeizu());
            row.createCell(218).setCellValue(appMarketDto.getJqfWandoujia());
            row.createCell(219).setCellValue(appMarketDto.getJqfBaidu());
            row.createCell(220).setCellValue(appMarketDto.getJqfOther());

            row.createCell(221).setCellValue(appMarketDto.getDkhHuawei());
            row.createCell(222).setCellValue(appMarketDto.getDkhXiaomi());
            row.createCell(223).setCellValue(appMarketDto.getDkhVivo());
            row.createCell(224).setCellValue(appMarketDto.getDkhOppo());
            row.createCell(225).setCellValue(appMarketDto.getDkh360());
            row.createCell(226).setCellValue(appMarketDto.getDkhYyb());
            row.createCell(227).setCellValue(appMarketDto.getDkhApple());
            row.createCell(228).setCellValue(appMarketDto.getDkhMeizu());
            row.createCell(229).setCellValue(appMarketDto.getDkhWandoujia());
            row.createCell(230).setCellValue(appMarketDto.getDkhBaidu());
            row.createCell(231).setCellValue(appMarketDto.getDkhOther());

            row.createCell(232).setCellValue(appMarketDto.getMdbHuawei());
            row.createCell(233).setCellValue(appMarketDto.getMdbXiaomi());
            row.createCell(234).setCellValue(appMarketDto.getMdbVivo());
            row.createCell(235).setCellValue(appMarketDto.getMdbOppo());
            row.createCell(236).setCellValue(appMarketDto.getMdb360());
            row.createCell(237).setCellValue(appMarketDto.getMdbYyb());
            row.createCell(238).setCellValue(appMarketDto.getMdbApple());
            row.createCell(239).setCellValue(appMarketDto.getMdbMeizu());
            row.createCell(240).setCellValue(appMarketDto.getMdbWandoujia());
            row.createCell(241).setCellValue(appMarketDto.getMdbBaidu());
            row.createCell(242).setCellValue(appMarketDto.getMdbOther());

            row.createCell(243).setCellValue(appMarketDto.getXedHuawei());
            row.createCell(244).setCellValue(appMarketDto.getXedXiaomi());
            row.createCell(245).setCellValue(appMarketDto.getXedVivo());
            row.createCell(246).setCellValue(appMarketDto.getXedOppo());
            row.createCell(247).setCellValue(appMarketDto.getXed360());
            row.createCell(248).setCellValue(appMarketDto.getXedYyb());
            row.createCell(249).setCellValue(appMarketDto.getXedApple());
            row.createCell(250).setCellValue(appMarketDto.getXedMeizu());
            row.createCell(251).setCellValue(appMarketDto.getXedWandoujia());
            row.createCell(252).setCellValue(appMarketDto.getXedBaidu());
            row.createCell(253).setCellValue(appMarketDto.getXedOther());

//            row.createCell(257).setCellValue(appMarketDto.getJqsHuawei());
//            row.createCell(258).setCellValue(appMarketDto.getJqsXiaomi());
//            row.createCell(259).setCellValue(appMarketDto.getJqsVivo());
//            row.createCell(260).setCellValue(appMarketDto.getJqsOppo());
//            row.createCell(261).setCellValue(appMarketDto.getJqs360());
//            row.createCell(262).setCellValue(appMarketDto.getJqsYyb());
//            row.createCell(263).setCellValue(appMarketDto.getJqsApple());
//            row.createCell(264).setCellValue(appMarketDto.getJqsMeizu());
//            row.createCell(265).setCellValue(appMarketDto.getJqsWandoujia());
//            row.createCell(266).setCellValue(appMarketDto.getJqsBaidu());
//            row.createCell(267).setCellValue(appMarketDto.getJqsOther());
//
//            row.createCell(268).setCellValue(appMarketDto.getJdjqHuawei());
//            row.createCell(269).setCellValue(appMarketDto.getJdjqXiaomi());
//            row.createCell(270).setCellValue(appMarketDto.getJdjqVivo());
//            row.createCell(271).setCellValue(appMarketDto.getJdjqOppo());
//            row.createCell(272).setCellValue(appMarketDto.getJdjq360());
//            row.createCell(273).setCellValue(appMarketDto.getJdjqYyb());
//            row.createCell(274).setCellValue(appMarketDto.getJdjqApple());
//            row.createCell(275).setCellValue(appMarketDto.getJdjqMeizu());
//            row.createCell(276).setCellValue(appMarketDto.getJdjqWandoujia());
//            row.createCell(277).setCellValue(appMarketDto.getJdjqBaidu());
//            row.createCell(278).setCellValue(appMarketDto.getJdjqOther());
//
//            row.createCell(279).setCellValue(appMarketDto.getDktHuawei());
//            row.createCell(280).setCellValue(appMarketDto.getDktXiaomi());
//            row.createCell(281).setCellValue(appMarketDto.getDktVivo());
//            row.createCell(282).setCellValue(appMarketDto.getDktOppo());
//            row.createCell(283).setCellValue(appMarketDto.getDkt360());
//            row.createCell(284).setCellValue(appMarketDto.getDktYyb());
//            row.createCell(285).setCellValue(appMarketDto.getDktApple());
//            row.createCell(286).setCellValue(appMarketDto.getDktMeizu());
//            row.createCell(287).setCellValue(appMarketDto.getDktWandoujia());
//            row.createCell(288).setCellValue(appMarketDto.getDktBaidu());
//            row.createCell(289).setCellValue(appMarketDto.getDktOther());
//
//            row.createCell(290).setCellValue(appMarketDto.getHdbHuawei());
//            row.createCell(291).setCellValue(appMarketDto.getHdbXiaomi());
//            row.createCell(292).setCellValue(appMarketDto.getHdbVivo());
//            row.createCell(293).setCellValue(appMarketDto.getHdbOppo());
//            row.createCell(294).setCellValue(appMarketDto.getHdb360());
//            row.createCell(295).setCellValue(appMarketDto.getHdbYyb());
//            row.createCell(296).setCellValue(appMarketDto.getHdbApple());
//            row.createCell(297).setCellValue(appMarketDto.getHdbMeizu());
//            row.createCell(298).setCellValue(appMarketDto.getHdbWandoujia());
//            row.createCell(299).setCellValue(appMarketDto.getHdbBaidu());
//            row.createCell(300).setCellValue(appMarketDto.getHdbOther());
//
//            row.createCell(301).setCellValue(appMarketDto.getYjxHuawei());
//            row.createCell(302).setCellValue(appMarketDto.getYjxXiaomi());
//            row.createCell(303).setCellValue(appMarketDto.getYjxVivo());
//            row.createCell(304).setCellValue(appMarketDto.getYjxOppo());
//            row.createCell(305).setCellValue(appMarketDto.getYjx360());
//            row.createCell(306).setCellValue(appMarketDto.getYjxYyb());
//            row.createCell(307).setCellValue(appMarketDto.getYjxApple());
//            row.createCell(308).setCellValue(appMarketDto.getYjxMeizu());
//            row.createCell(309).setCellValue(appMarketDto.getYjxWandoujia());
//            row.createCell(310).setCellValue(appMarketDto.getYjxBaidu());
//            row.createCell(311).setCellValue(appMarketDto.getYjxOther());

            for(int k=0;k<254;k++) {
                row.getCell(k).setCellStyle(style);
            }

            rowNum++;
        }
    }
}
