package com.lubandj.master.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ${zhaoshuzhen} on 2018/1/12.
 */

public class StringUtil {
    /**
     * 时间格式转毫秒
     */
    public static  long getTimeMill(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;
    }

}
