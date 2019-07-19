package com.cw.core.common.util;//package com.laman.fusion.base.util;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.Random;
//
//@Slf4j
//public class RandomValidateCodeUtil {
//
//    public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";//放到session中的key
//    private static String randString = "0123456789";//随机产生只有数字的字符串 private String
//    //private String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生只有字母的字符串
//    //private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
//    private static int width = 190;// 图片宽
//    private static int height = 50;// 图片高
//    private static int lineSize = 40;// 干扰线数量
//    private static int stringNum = 4;// 随机产生字符数量
//
//
//    private static Random random = new Random();
//
//    /**
//     * 获得字体
//     */
//    private static Font getFont() {
//        return new Font("Fixedsys", Font.CENTER_BASELINE, 38);
//    }
//
//    /**
//     * 获得颜色
//     */
//    private static Color getRandColor(int fc, int bc) {
//        if (fc > 255)
//            fc = 255;
//        if (bc > 255)
//            bc = 255;
//        int r = fc + random.nextInt(bc - fc - 16);
//        int g = fc + random.nextInt(bc - fc - 14);
//        int b = fc + random.nextInt(bc - fc - 18);
//        return new Color(r, g, b);
//    }
//
//    /**
//     * 生成随机图片
//     */
//    public static void getRandcode(HttpServletRequest request, HttpServletResponse response) {
//        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
//        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expire", 0);
//        HttpSession session = request.getSession();
//        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
//        g.fillRect(0, 0, width, height);//图片大小
//        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 28));//字体大小
//        g.setColor(getRandColor(110, 133));//字体颜色
//        // 绘制干扰线
//        for (int i = 0; i <= lineSize; i++) {
//            drowLine(g);
//        }
//        // 绘制随机字符
//        String randomString = "";
//        for (int i = 1; i <= stringNum; i++) {
//            randomString = drowString(g, randomString, i);
//        }
//        log.info(randomString);
//        //将生成的随机字符串保存到session中
//        session.removeAttribute(RANDOMCODEKEY);
//        session.setAttribute(RANDOMCODEKEY, randomString);
//        g.dispose();
//        try {
//            // 将内存中的图片通过流动形式输出到客户端
//            ImageIO.write(image, "JPEG", response.getOutputStream());
//        } catch (Exception e) {
//            log.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
//        }
//
//    }
//
//    /**
//     * 绘制字符串
//     */
//    private static String drowString(Graphics g, String randomString, int i) {
//        g.setFont(getFont());
//        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
//                .nextInt(121)));
//        String rand = String.valueOf(getRandomString(random.nextInt(randString
//                .length())));
//        randomString += rand;
//        g.translate(random.nextInt(3), random.nextInt(3));
//        g.drawString(rand, 33 * i, 32);
//        return randomString;
//    }
//
//    /**
//     * 绘制干扰线
//     */
//    private static void drowLine(Graphics g) {
//        int x = random.nextInt(width);
//        int y = random.nextInt(height);
//        int xl = random.nextInt(13);
//        int yl = random.nextInt(15);
//        g.drawLine(x, y, x + xl, y + yl);
//    }
//
//    /**
//     * 获取随机的字符
//     */
//    public static String getRandomString(int num) {
//        return String.valueOf(randString.charAt(num));
//    }
//
//}
