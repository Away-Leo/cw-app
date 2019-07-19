package com.cw.core.common.util;

/**
 * User 
 * Date 2016/11/2.
 */

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {

    public UUIDUtil() {
    }

    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }

    public static String generateCode() {
        java.text.SimpleDateFormat sfdate = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String code = sfdate.format(new Date());
        float fl = new Random().nextFloat();
        fl = fl < 0.1 ? (float) (fl + 0.1) : fl;
        Integer ran = Integer.parseInt((fl * 1000000 + "").split("\\.")[0]);
        char[] temp = code.substring(code.length() - 6, code.length()).toCharArray();
        for (char c : temp) {
            int i = new Random().nextInt(6);
            ran += Integer.parseInt(((Integer.parseInt(c + "") * Math.pow(10, i)) + "").split("\\.")[0]);
        }
        code = code.substring(0, code.length() - 6) + ran;
        String buwei = "";
        int codel = code.length();
        if (codel < 24) {
            double f = Math.pow(10, 24 - codel);
            fl = new Random().nextFloat();
            fl = fl < 0.1 ? (float) (fl + 0.1) : fl;
            String a1 = fl * f + "";
            buwei = a1.split("\\.")[0];
        }
        return code + buwei;
    }

    public static String createVerifyCode()
    {
        String result = null;

        try{
            Random rad=new Random();
            result  = rad.nextInt(1000000) +"";

            if(result.length()!=6){
                return createVerifyCode();
            }

        }catch (Exception e)
        {
            System.err.println("create verify code error：："+e);
        }
        return result;
    }

    public static Long generateId() {
        String code = String.valueOf(new Date().getTime());
        float fl = new Random().nextFloat();
        fl = fl < 0.1 ? (float) (fl + 0.1) : fl;
        Integer ran = Integer.parseInt((fl * 1000000 + "").split("\\.")[0]);
        char[] temp = code.substring(code.length() - 6, code.length()).toCharArray();
        for (char c : temp) {
            int i = new Random().nextInt(6);
            ran += Integer.parseInt(((Integer.parseInt(c + "") * Math.pow(10, i)) + "").split("\\.")[0]);
        }
        code = code.substring(0, code.length() - 6) + ran;
        String buwei = "";
        int codel = code.length();
        if (codel < 17) {
            double f = Math.pow(10, 17 - codel);
            fl = new Random().nextFloat();
            fl = fl < 0.1 ? (float) (fl + 0.1) : fl;
            String a1 = fl * f + "";
            buwei = a1.split("\\.")[0];
        }
        return Long.parseLong(code+buwei);
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    public static void main(String[] args) {
//        Set<Long> set = new HashSet<Long>();
//        Long start = new Date().getTime();
//        for (int i = 0; i < 40000; i++) {
//            Long code = generateId();
//            if(set.contains(code)){
//                System.out.println("哟重复");
//                System.out.println(code);
//            }else {
//                set.add(code);
//            }
//        }
//        Long end = new Date().getTime();
//        System.out.println(end-start);
//        System.out.println(set.size());
        System.out.println(getUUID());
    }

    public static String getOrderCode() {
        java.text.SimpleDateFormat sfdate = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String code = sfdate.format(new Date()) + (int) (Math.random() * 9000 + 1000);
        return code;
    }
}
