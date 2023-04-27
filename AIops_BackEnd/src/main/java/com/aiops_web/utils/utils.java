package com.aiops_web.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Utils {

    // 获取当前时间
    public Timestamp getCurrentTstamp() {
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
//        System.out.println(time);//2017-05-06 15:54:21.0
        return time;
    }
}
