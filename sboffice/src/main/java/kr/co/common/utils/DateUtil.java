package kr.co.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import kr.co.common.utils.spring.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil extends DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
    private static final String DEFAULT_YMD_FORMAT = "yyyyMMdd";
    private static final String DEFAULT_TIME_FORMAT = "HHmmss";
    private static final String DEFAULT_TIME_MS_FORMAT = "HHmmssSSS";
    
    /**
     * Date객체 반환
     * 
     * @return Date
     */
    private static Date currentDate() {
        return new Date();
    }

    /**
     * 현재 일자 가져오기
     * 
     * @return 현재 일자 String
     */
    public static String currentDateString() {
        return date2string(currentDate(), DEFAULT_YMD_FORMAT);
    }

    /**
     * 현재 일자 시간 가져오기
     * 
     * @return 현재 일자 시간 String
     */
    public static String currentDateTimeString() {
        return date2string(currentDate(), DEFAULT_DATE_FORMAT);
    }
    
    /**
      * 
      * 
      * @return 현재 시간 String
      */
    public static String currentTimeString() {
        return date2string(currentDate(), DEFAULT_TIME_FORMAT);
    }
    
    /**
      * 
      * @return
      */
    public static String currentTimeMsString() {
        return date2string(currentDate(), DEFAULT_TIME_MS_FORMAT);
    }

    /**
     * 년 변경 (날짜 포맷 필요)
     * 
     * @param amount int
     * @param pattern String
     * @return Date
     */
    public static String addYear(int amount,String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate());
        cal.add(Calendar.YEAR, amount);
        
        DateFormat df = new SimpleDateFormat(pattern);
        
        return df.format(cal.getTime());
    }
    
    /**
     * 월 변경 (날짜 포맷 필요)
     * 
     * @param amount int
     * @param pattern String
     * @return Date
     */
    public static String addMonth(int amount,String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate());
        cal.add(Calendar.MONTH, amount);
        
        DateFormat df = new SimpleDateFormat(pattern);
        
        return df.format(cal.getTime());
    }
    
    /**
     * 일 변경 (날짜 포맷 필요)
     * 
     * @param amount int
     * @param pattern String
     * @return Date
     */
    public static String addDays(int amount,String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate());
        cal.add(Calendar.DATE, amount);
        
        DateFormat df = new SimpleDateFormat(pattern);
        
        return df.format(cal.getTime());
    }
    
    /**
     * 일 변경
     * 
     * @param amount int
     * @return date String
     */
    public static String addDaysString(int amount) {
        return addDaysString(currentDate(), amount, DEFAULT_YMD_FORMAT);
    }

    /**
     * 일 변경
     * 
     * @param date String
     * @param amount int
     * @return date String
     */
    public static String addDaysString(String date, int amount) {
        return addDaysString(string2date(date, DEFAULT_YMD_FORMAT), amount, DEFAULT_YMD_FORMAT);
    }

    /**
     * 일 변경
     * 
     * @param date String
     * @param amount int
     * @param pattern String
     * @return date String
     */
    public static String addDaysString(String date, int amount, String pattern) {
        return addDaysString(string2date(date, pattern), amount, pattern);
    }

    /**
     * 일 변경
     * 
     * @param date Date
     * @param amount int
     * @param pattern String
     * @return date String
     */
    private static String addDaysString(Date date, int amount, String pattern) {
        return date2string(addDays(date, amount), pattern);
    }

    /**
     * 날짜 가져오기
     * 
     * @param date String
     * @return date Date
     */
    public static Date getDate(String date) {
        return string2date(date, DEFAULT_YMD_FORMAT);
    }

    /**
     * 날짜 시간 가져오기
     * 
     * @param date String
     * @return date Date
     */
    public static Date getDatetime(String date) {
        return string2date(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 시간 가져오기
     * 
     * @param time String
     * @return date Date
     */
    public static Date getTime(String time) {
        return string2date(time, DEFAULT_TIME_FORMAT);
    }
    
    /**
     * 날짜 가져오기 ( 날짜 포맷 필요 )
     * 
     * @param date String
     * @param pattern String
     * @return date Date
     */
    public static Date string2date(String date, String pattern) {
        // if( StringUtil.isEmpty(date) || StringUtil.isEmpty(pattern) )
        if (StringUtil.isEmpties(date, pattern))
            return null;

        try {
            return parseDate(date, pattern);
        } catch (ParseException e) {
            if (log.isErrorEnabled())
                log.error("getDate Fail : {}, {}", date, pattern);

            return null;
        }
    }

    /**
     * Date &gt; String 변환
     * 
     * @param date Date
     * @param pattern String
     * @return String 형 날짜
     */
    public static String date2string(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 입력일자가 있는 달의 총 일수 구하기
     * 
     * @param date(YYYYMMDD) String
     * @return int 일수
     */
    public static int getLastDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
                Integer.parseInt(date.substring(6, 8)));

        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 주어진 날짜가 속한 주의 특정 요일 일자을 구함 <br>
     * 
     * @param date String yyyymmdd 형식의 날짜
     * @param dayOfWeek int 요일 (Calendar.SUNDAY ~ SATURDAY 중 하나)
     * @return yyyyMMdd 형식의 8자리 날짜
     */
    public static String getDayOfWeek(String date, int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
                Integer.parseInt(date.substring(6, 8)));

        int gap = 0;
        int day = cal.get(Calendar.DAY_OF_WEEK);

        if (day == dayOfWeek)
            return date;
        else if (dayOfWeek == Calendar.SUNDAY)
            gap = 8 - day;
        else if (day < dayOfWeek)
            gap = dayOfWeek - day;
        else
            gap = -(day - dayOfWeek);

        if (day == Calendar.SUNDAY)
            gap = gap - 7;
        return addDaysString(date, gap);
    }

    /**
     * 주어진 날짜의 요일을 구함
     * 
     * @param date String yyyymmdd 형식의 날짜
     * @return int 요일 (Calendar.SUNDAY ~ SATURDAY 중 하나)
     */
    public static int getDay(String date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
                Integer.parseInt(date.substring(6, 8)));
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
