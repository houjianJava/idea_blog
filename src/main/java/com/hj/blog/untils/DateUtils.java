package com.hj.blog.untils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String dateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMMM-dd HH:mm:ss");
        return format.format(date);
    }
}
