package com.lanxi.equity.assist;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yangyuanjian created in 2018/2/11 17:17
 */
public interface TimeAssist {
    static DateTimeFormatter DAY_FORMATTER=DateTimeFormatter.ofPattern("yyyyMMdd");
    static DateTimeFormatter TIME_FORMATTER=DateTimeFormatter.ofPattern("HHmmss");

    static String today(){
        return DAY_FORMATTER.format(LocalDateTime.now());
    }

    static String nDayLater(int n){
        return DAY_FORMATTER.format(LocalDateTime.now().plusDays(n));
    }

    static String now(){
        return TIME_FORMATTER.format(LocalDateTime.now());
    }
}
