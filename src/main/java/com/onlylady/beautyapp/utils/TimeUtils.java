package com.onlylady.beautyapp.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/3.
 * 时间工具类
 */
public class TimeUtils {
    public static TimeUtils timeUtils;

    public static TimeUtils getInstance() {
        if (timeUtils == null) {
            timeUtils = new TimeUtils();
        }
        return timeUtils;
    }

    public Date timeStamp2Date(long pt) {
        if (pt == 0) {
            return null;
        }
        Long timestamp = (long) pt * 1000;
        Date date = new Date(timestamp);
        return date;
    }

    public Date timeStamp2Date(String pt1) {
        Long pt = 0l;
        try {
            pt = Long.parseLong(pt1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (pt == 0) {
            return null;
        }
        Long timestamp = (long) pt * 1000;
        Date date = new Date(timestamp);
        return date;
    }

    public String getRiqiandWeek(String pt) {
        if (TextUtils.isEmpty(pt)) {
            return "";
        }
        int pp = Integer.parseInt(pt);
        Date date = timeStamp2Date(pp);
        Date datenow = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t = format.format(date);
        String tnow = format.format(datenow);
        String Week = "星期";
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(t));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (t.substring(0, 8).equals(tnow.substring(0, 8))) {
            int d1 = Integer.parseInt(t.substring(8, t.length()));
            int dnow = Integer.parseInt(tnow.substring(8, tnow.length()));
            if (dnow - d1 == 1) {
                return t + "/昨天";
            } else if (dnow - d1 == 2) {
                return t + "/前天";
            } else if (dnow - d1 == 0) {
                return t + "/今天";
            } else if (dnow - d1 == -1) {
                return t + "/明天";
            }
        }

        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                Week += "天";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }


        return t + "/" + Week;

    }

    public String getRiqiandTime(String pt) {
        if (TextUtils.isEmpty(pt)) {
            return "";
        }
        int pp = Integer.parseInt(pt);
        Date date = timeStamp2Date(pp);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd/HH:mm");
        String t = format.format(date);

        return t;

    }

    public String getTime(String pt) {
        if (TextUtils.isEmpty(pt)) {
            return "";
        }
        int pp = Integer.parseInt(pt);
        Date date = timeStamp2Date(pp);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String t = format.format(date);
        return t;

    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm");
        return format.format(date);
    }

    // 将字符串转为时间戳


    public String getTimeChuo(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd/HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);


        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    /* public  int isYesterday(long time) {
         Log.i("info",""+timeStamp2Date(time));
         Date date = timeStamp2Date(time);

         int day = 0;
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         Date d1 = new Date();//当前时间
         Date d2 = null;//传进的时间
         try {
             d2 = sdf.parse(date.toString());
         } catch (ParseException e) {
             e.printStackTrace();
         }
         long cha = d2.getRiqiandWeek() - d1.getRiqiandWeek();
         long l = System.currentTimeMillis();

         day = new Long(cha / (1000 * 60 * 60 * 24)).intValue();
         return day;
     }*/
    @SuppressLint("SimpleDateFormat")
    public boolean isSameDay(int pt) {

        Date date = timeStamp2Date(pt);
        Date sameDate = new Date();
        if (null == date || null == sameDate) {

            return false;

        }

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)

                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {

            return true;

        }
        return false;

    }

    // a integer to xx:xx:xx
    public String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;

        minute = time / 60;

        hour = minute / 60;
        if (hour > 99)
            return "99:59:59";
        minute = minute % 60;
        second = time - hour * 3600 - minute * 60;
        timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);

        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
