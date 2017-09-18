package com.aihuishou.commonlib.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类名称：DateUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：日期工具类
 */
public class DateUtil {

   public static Date addMonthOnNow(int month ) {
       Calendar cal = Calendar.getInstance();

       Date date = new Date();
       cal.setTime( date );

       cal.add(Calendar.MONTH, month);
       Date result = cal.getTime();

       return result;
   }


    public static String formatDate( Date date ) {
        return formatDate( date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate( Date date, String format ) {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        String result = sdf.format( date );
        return result;
    }
    public static String formatString(String min){

        if(min.length()==1){
            return "0"+min;
        }else{
            return min;
        }
    }

}
