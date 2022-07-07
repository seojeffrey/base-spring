package jeff.baseproject.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

/*
Time 을 관리하는 클래스
테스트를 시간변경 로직이 들어갈 수 있기에
한개의 클래스로 묶어서 관리한다
* */

public class Time {
    public final static int DAY_TO_HOUR = 24;
    public final static int DAY_TO_MINUTE = 1440;
    public final static int DAY_TO_SECONDS = 86400;
    public final static int HOUR_TO_SECONDS = 3600;
    public final static int MINUTE_TO_SECONDS = 60;

    private final static String DEV_TIME_CUSTOM_KEY = "DEV_TIME_CUSTOM";  // 시간 변경을 위한 REDIS_KEY

    public final static String DATE_TEMPLATE = "%Y-%m-%d";
    public final static String DATETIME_TEMPLATE = "%Y-%m-%d %H:%M:%S";

    public static long getTimeStamp(){
        return System.currentTimeMillis();
    }

    public static LocalDateTime fromTimeStamp(long timeStamp){
        return LocalDateTime.ofEpochSecond(timeStamp, 0, UTC);
    }

    public static LocalDateTime now(){
        return LocalDateTime.now();
    }


    public static String dateToString(Date date){
        SimpleDateFormat transFormat = new SimpleDateFormat(DATE_TEMPLATE);
        return transFormat.format(date);

    }
    public static String dateTimeToString(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern(DATETIME_TEMPLATE));
    }
    public static LocalDateTime stringFromDate(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATETIME_TEMPLATE));


    }
}
