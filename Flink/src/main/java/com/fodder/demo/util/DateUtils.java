package com.fodder.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wufc
 * @create 2020-04-19 1:59 上午
 */
public class DateUtils {
    public static Long getTimeStamp(String dateTime) throws ParseException {
        Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateTime);
        Long timeStamp = parse.getTime();
        return timeStamp;
    }

    public static String getDate(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String sd = sdf.format(timeStamp);
        return sd;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtils.getTimeStamp("2020-04-19 01:12"));
    }
}
