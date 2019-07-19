package com.cw.core.common.util;

import com.google.common.base.Preconditions;
import com.zds.common.util.ShutdownHooks;
import com.zds.common.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateHelper {
    public static final String simple = "yyyy-MM-dd HH:mm:ss";
    public static final String dtSimple = "yyyy-MM-dd";
    public static final String dtSimpleYm = "yyyy-MM";
    public static final String dtSimpleSlash = "yyyy/MM/dd";
    public static final String dtSimpleChinese = "yyyy年MM月dd日";
    public static final String week = "EEEE";
    public static final String dtShort = "yyyyMMdd";
    public static final String dtLong = "yyyyMMddHHmmss";
    public static final String hmsFormat = "HH:mm:ss";
    public static final String hmsFormat1 = "HHmmss";
    public static final String simpleFormat = "yyyy-MM-dd HH:mm";
    protected static final ThreadLocal<ConcurrentMap<String, DateFormat>> formaterCache = new ThreadLocal<ConcurrentMap<String, DateFormat>>() {
        protected ConcurrentMap<String, DateFormat> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };
    public static final long MILL_SECOND_IN_DAY = 86400000L;
    public static Date MAX_DAY = null;

    public static final DateFormat getFormat(String format) {
        DateFormat dateFormat = (DateFormat)((ConcurrentMap)formaterCache.get()).get(format);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            ((ConcurrentMap)formaterCache.get()).put(format, dateFormat);
        }

        return (DateFormat)dateFormat;
    }

    public static final String simpleFormat(Date date) {
        return date == null ? "" : getFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static final String dtSimpleFormat(Date date) {
        return date == null ? "" : getFormat("yyyy-MM-dd").format(date);
    }

    public static final String dtSimpleYmFormat(Date date) {
        return date == null ? "" : getFormat("yyyy-MM").format(date);
    }

    public static final Date strToDtSimpleYmFormat(String strDate) {
        if (strDate == null) {
            return null;
        } else {
            try {
                return getFormat("yyyy-MM").parse(strDate);
            } catch (Exception var2) {
                return null;
            }
        }
    }

    public static final Date strToDtSimpleFormat(String strDate) {
        if (strDate == null) {
            return null;
        } else {
            try {
                return getFormat("yyyy-MM-dd").parse(strDate);
            } catch (Exception var2) {
                return null;
            }
        }
    }

    public static final String dtSimpleSlashFormat(Date date) {
        return date == null ? "" : getFormat("yyyy/MM/dd").format(date);
    }

    public static final Date strToDtSimpleSlashFormat(String strDate) {
        if (strDate == null) {
            return null;
        } else {
            try {
                return getFormat("yyyy/MM/dd").parse(strDate);
            } catch (Exception var2) {
                return null;
            }
        }
    }

    public static final String getDiffDate(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(5, idiff);
        return dtSimpleFormat(c.getTime());
    }

    public static final String getDiffMon(Date dt, int idiff) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(2, idiff);
        return dtSimpleFormat(c.getTime());
    }

    public static final String dtSimpleChineseFormat(Date date) {
        return date == null ? "" : getFormat("yyyy年MM月dd日").format(date);
    }

    public static final String dtSimpleChineseFormatStr(String date) throws ParseException {
        return date == null ? "" : getFormat("yyyy年MM月dd日").format(string2Date(date));
    }

    public static final Date string2Date(String stringDate) throws ParseException {
        return stringDate == null ? null : getFormat("yyyy-MM-dd").parse(stringDate);
    }

    public static final Date string2DateTime(String stringDate) throws ParseException {
        return stringDate == null ? null : getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
    }

    public static final Date string2DateTimeByAutoZero(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        } else {
            if (stringDate.length() == 11) {
                stringDate = stringDate + "00:00:00";
            } else if (stringDate.length() == 13) {
                stringDate = stringDate + ":00:00";
            } else if (stringDate.length() == 16) {
                stringDate = stringDate + ":00";
            } else if (stringDate.length() == 10) {
                stringDate = stringDate + " 00:00:00";
            }

            return getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
        }
    }

    public static final Date string2DateTimeBy23(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        } else {
            if (stringDate.length() == 11) {
                stringDate = stringDate + "23:59:59";
            } else if (stringDate.length() == 13) {
                stringDate = stringDate + ":59:59";
            } else if (stringDate.length() == 16) {
                stringDate = stringDate + ":59";
            } else if (stringDate.length() == 10) {
                stringDate = stringDate + " 23:59:59";
            }

            return getFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
        }
    }

    public static final int calculateDecreaseDate(String beforDate, String afterDate) throws ParseException {
        Date date1 = getFormat("yyyy-MM-dd").parse(beforDate);
        Date date2 = getFormat("yyyy-MM-dd").parse(afterDate);
        long decrease = getDateBetween(date1, date2) / 1000L / 3600L / 24L;
        int dateDiff = (int)decrease;
        return dateDiff;
    }

    public static final long getDateBetween(Date dBefor, Date dAfter) {
        long lBefor = 0L;
        long lAfter = 0L;
        long lRtn = 0L;
        lBefor = dBefor.getTime();
        lAfter = dAfter.getTime();
        lRtn = lAfter - lBefor;
        return lRtn;
    }

    public static final Date shortstring2Date(String stringDate) throws ParseException {
        return stringDate == null ? null : getFormat("yyyyMMdd").parse(stringDate);
    }

    public static final String shortDate(Date Date) {
        return Date == null ? null : getFormat("yyyyMMdd").format(Date);
    }

    public static final String longDate(Date Date) {
        return Date == null ? null : getFormat("yyyyMMddHHmmss").format(Date);
    }

    public static final Long string2DateLong(String stringDate) throws ParseException {
        Date d = string2Date(stringDate);
        return d == null ? null : d.getTime();
    }

    public static final String hmsFormat(Date date) {
        return date == null ? "" : getFormat("HH:mm:ss").format(date);
    }

    public static final String simpleDate(Date date) {
        return date == null ? "" : getFormat("yyyy-MM-dd HH:mm").format(date);
    }

    public static final Date simpleFormatDate(String dateString) throws ParseException {
        return dateString != null && dateString.trim().length() != 0 ? getFormat("yyyy-MM-dd HH:mm").parse(dateString.trim()) : null;
    }

    public static final String getDiffDate(int diff) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, diff);
        return dtSimpleFormat(c.getTime());
    }

    public static final Date getDiffDateTime(int diff) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, diff);
        return c.getTime();
    }

    public static final String getDiffDateTime(int diff, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, diff);
        c.add(10, hours);
        return dtSimpleFormat(c.getTime());
    }

    public static final Long dateToNumber(Date date) {
        if (date == null) {
            return null;
        } else {
            String formated = getFormat("yyyyMMdd").format(date);
            return Long.valueOf(formated);
        }
    }

    public static String getNextMon(String StringDate) throws ParseException {
        Date tempDate = shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();
        cad.setTime(tempDate);
        cad.add(2, 1);
        return shortDate(cad.getTime());
    }

    public static String getNextDay(String StringDate) throws ParseException {
        Date tempDate = string2Date(StringDate);
        Calendar cad = Calendar.getInstance();
        cad.setTime(tempDate);
        cad.add(5, 1);
        return dtSimpleFormat(cad.getTime());
    }

    public static String getWebNextDayString() {
        Calendar cad = Calendar.getInstance();
        cad.setTime(new Date());
        cad.add(5, 1);
        return dtSimpleFormat(cad.getTime());
    }

    public static String getNextDay(Date date) throws ParseException {
        Calendar cad = Calendar.getInstance();
        cad.setTime(date);
        cad.add(5, 1);
        return dtSimpleFormat(cad.getTime());
    }

    public static Date getNextDayDtShort(String StringDate) throws ParseException {
        Date tempDate = shortstring2Date(StringDate);
        Calendar cad = Calendar.getInstance();
        cad.setTime(tempDate);
        cad.add(5, 1);
        return cad.getTime();
    }

    public static long countDays(String startDate, String endDate) {
        Date tempDate1 = null;
        Date tempDate2 = null;
        long days = 0L;

        try {
            tempDate1 = string2Date(startDate);
            tempDate2 = string2Date(endDate);
            days = (tempDate2.getTime() - tempDate1.getTime()) / 86400000L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return days;
    }

    public static int countDays(Date dateStart, Date dateEnd) {
        return dateStart != null && dateEnd != null ? (int)((dateEnd.getTime() - dateStart.getTime()) / 86400000L) : -1;
    }

    public static int countDaysBetweenTwoDays(Date dateStart, Date dateEnd) {
        if (dateStart != null && dateEnd != null) {
            dateEnd.setHours(0);
            dateEnd.setMinutes(0);
            dateEnd.setSeconds(0);
            dateStart.setHours(0);
            dateStart.setMinutes(0);
            dateStart.setSeconds(0);
            return (int)((dateEnd.getTime() / 1000L - dateStart.getTime() / 1000L) / 86400L);
        } else {
            return -1;
        }
    }

    public static boolean checkDays(Date start, Date end, int days) {
        int g = countDays(start, end);
        return g <= days;
    }

    public static Date now() {
        return new Date();
    }

    public static String nowStr() {
        return shortDate(new Date());
    }

    public static Date tomorrow() {
        Calendar cad = Calendar.getInstance();
        cad.setTime(new Date());
        cad.add(5, 1);
        return cad.getTime();
    }

    public static String getDiffStringDate(Date dt, int diff) {
        Calendar ca = Calendar.getInstance();
        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }

        ca.add(5, diff);
        return dtSimpleFormat(ca.getTime());
    }

    public static boolean checkTime(String statTime) {
        if (statTime.length() > 8) {
            return false;
        } else {
            String[] timeArray = statTime.split(":");
            if (timeArray.length != 3) {
                return false;
            } else {
                for(int i = 0; i < timeArray.length; ++i) {
                    String tmpStr = timeArray[i];

                    try {
                        Integer tmpInt = Integer.valueOf(tmpStr);
                        if (i == 0) {
                            if (tmpInt > 23 || tmpInt < 0) {
                                return false;
                            }
                        } else if (tmpInt > 59 || tmpInt < 0) {
                            return false;
                        }
                    } catch (Exception var5) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static final String stringToStringDate(String stringDate) {
        if (stringDate == null) {
            return null;
        } else {
            return stringDate.length() != 8 ? null : stringDate.substring(0, 4) + stringDate.substring(4, 6) + stringDate.substring(6, 8);
        }
    }

    public static Date string2Date(String str, String format) {
        try {
            return getFormat(format).parse(str);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static final Date increaseDate(Date aDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.add(5, days);
        return cal.getTime();
    }

    public static final boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static final boolean isDefaultWorkingDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(7);
        return week != 7 && week != 1;
    }

    public static final String getWeekDay(Date date) {
        return getFormat("EEEE").format(date);
    }

    public static Date parseDateNoTime(String sDate, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        } else {
            DateFormat dateFormat = getFormat(format);
            if (sDate != null && sDate.length() >= format.length()) {
                return dateFormat.parse(sDate);
            } else {
                throw new ParseException("length too little", 0);
            }
        }
    }

    public static final String getNowDateForPageSelectAhead() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(12) < 30) {
            cal.set(12, 0);
        } else {
            cal.set(12, 30);
        }

        return simpleDate(cal.getTime());
    }

    public static final boolean isIn(Date cur, Date start, Date end) {
        Preconditions.checkNotNull(cur, "cur不能为null");
        Preconditions.checkNotNull(start, "start不能为null");
        Preconditions.checkNotNull(end, "end不能为null");
        return cur.after(start) && cur.before(end);
    }

    public static final String getNowDateForPageSelectBehind() {
        Calendar cal = Calendar.getInstance();
        if (cal.get(12) < 30) {
            cal.set(12, 30);
        } else {
            cal.set(11, cal.get(11) + 1);
            cal.set(12, 0);
        }

        return simpleDate(cal.getTime());
    }

    public static String getNewFormatDateString(Date date) {
        return getDateString(date, getFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        return date != null && dateFormat != null ? dateFormat.format(date) : null;
    }

    public static Date getOneMonthBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(2, cal.get(2) - 1);
        return cal.getTime();
    }

    public static Date getStartTimeOfTheDate(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(11, 0);
            c.set(12, 0);
            c.set(13, 0);
            c.set(14, 0);
            return c.getTime();
        }
    }

    public static Date getEndTimeOfTheDate(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(11, 23);
            c.set(12, 59);
            c.set(13, 59);
            c.set(14, 0);
            return c.getTime();
        }
    }

    public static Date getMaxDate() {
        return new Date(MAX_DAY.getTime());
    }

    public static boolean isMaxDate(Date date) {
        return MAX_DAY.equals(date);
    }

    public static Date nowIfNull(Date date) {
        return defaultIfNull(date, new Date());
    }

    public static Date defaultIfNull(Date date, Date defaultDate) {
        return date != null ? date : defaultDate;
    }

    public static boolean isDateBetween(Date date, Date startDate, Date endDate) {
        Preconditions.checkNotNull(date, "date不能为null");
        Preconditions.checkNotNull(startDate, "startDate不能为null");
        Preconditions.checkNotNull(endDate, "endDate不能为null");
        return date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime();
    }

    public static Date stringToDate(String model, String str) {
        Date td = null;
        SimpleDateFormat sdf = new SimpleDateFormat(model);

        try {
            td = sdf.parse(str);
        } catch (ParseException var5) {
            var5.printStackTrace();
            td = null;
        }

        return td;
    }

    static {
        try {
            MAX_DAY = string2DateTimeBy23("9999-12-31 23:59:59");
        } catch (ParseException var1) {
            ;
        }

        ShutdownHooks.addShutdownHook(new Runnable() {
            public void run() {
                if (formaterCache != null) {
                    formaterCache.remove();
                }

            }
        }, "DateUtilShutdownHook");
    }
}
