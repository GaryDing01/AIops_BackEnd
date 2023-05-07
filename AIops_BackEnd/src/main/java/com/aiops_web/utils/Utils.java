package com.aiops_web.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<String> String2List(String s1) {
        if (s1.length() < 2) {
            return null;
        }
        String s2 = s1.substring(1, s1.length() - 1);
        return Arrays.asList(s2.split(", "));
    }
}
