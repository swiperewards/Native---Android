package com.winjit.swiperewards.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class DateUtil {

    private static final String DisplayDateFormat = "dd MMM yyyy";
    public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat(DisplayDateFormat, Locale.US);
    private static final String apiFormat = "yyyy-MM-dd";
    public static final SimpleDateFormat API_FORMAT = new SimpleDateFormat(apiFormat, Locale.US);
    private static final String transactionDetailDateFormat = "dd-MM-yyyy hh:mm:ss";
    private static final String mandateTimeStampformat = "yyyy-MM-dd hh:mm:ss";
    public static final SimpleDateFormat MANDATE_TIME_STAMP_FORMAT = new SimpleDateFormat(mandateTimeStampformat, Locale.US);
    private static final String transactionDateFormat = "yyyy-MM-dd";
    private static final String transactionTimeFormat = "hh:mm:ss";
    private static final String rowMandateDateFormat = "MMM dd yyyy";

    public static final SimpleDateFormat MANDATE_ROW_DATE_FORMAT = new SimpleDateFormat(rowMandateDateFormat, Locale.US);
    private static final SimpleDateFormat simpleDateFormatTransaction = new SimpleDateFormat(transactionDateFormat, Locale.US);
    private static final SimpleDateFormat simpleTimeFormatTransaction = new SimpleDateFormat(transactionTimeFormat, Locale.US);
    private static final String mandateTimeStampFormatMilliSecond = "yyyy-MM-dd hh:mm:ss.SSS";
    public static final SimpleDateFormat MANDATE_TIME_STAMP_FORMAT_MILLISECOND = new SimpleDateFormat(mandateTimeStampFormatMilliSecond, Locale.US);
    private static final String mandateDetailTimestampFormat = "MMM dd yyyy hh:mm a";
    public static final SimpleDateFormat MANDATE_DETAIL_TIME_STAMP_FORMAT = new SimpleDateFormat(mandateDetailTimestampFormat, Locale.US);
    private static final SimpleDateFormat transactionDetailSDF = new SimpleDateFormat(transactionDetailDateFormat, Locale.US);

    private static String getCurrentDateInString() {

        Calendar calendar = Calendar.getInstance();
        return simpleDateFormatTransaction.format(calendar.getTime());
    }

    // Return a Date object which has no time factor
    public static Date getCurrentDate() {
        return getDateFromString(getCurrentDateInString());
    }

    public static String getDateInDisplayFormat(Date date) {
        if (date != null) {
            return DISPLAY_DATE_FORMAT.format(date.getTime());
        }
        return "";
    }


    public static String getDateDisplayFormat(Date date) {
        if (date != null) {
            return DISPLAY_DATE_FORMAT.format(date.getTime());
        }
        return "";
    }

    public static String getTransactionDetailDate(Date date) {
        if (date != null) {
            return transactionDetailSDF.format(date.getTime());
        }
        return "";
    }

    public static String getDateInTransactionFormat(Date date) {
        if (date != null) {
            return simpleDateFormatTransaction.format(date.getTime());
        }
        return "";
    }

    public static Date getDateFromString(String date) {
        Date dateObject = new Date();
        try {
            if (date != null) {
                dateObject = simpleDateFormatTransaction.parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObject;
    }


    public static Date getDateFromString(String date, SimpleDateFormat dateFormat) {
        Date dateObject = new Date();
        try {
            if (date != null) {
                dateObject = dateFormat.parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return dateObject;
    }

    private static Date getTimeFromString(String time) {
        Date dateObject = new Date();
        try {
            if (time != null) {
                dateObject = simpleTimeFormatTransaction.parse(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObject;
    }

    /**
     * This method returns current Date Time
     *
     * @return current device date time.
     */
    public static String getDeviceCurrentDateTime(DateFormat dateformat) {
        Calendar c = Calendar.getInstance();
        return dateformat.format(c.getTime());
    }

    public static Date getDateTimeFromString(String date, String time) {
        Calendar calendarDate = Calendar.getInstance();
        Date dtDate = getDateFromString(date);
        calendarDate.setTime(dtDate);

        Calendar calendarTime = Calendar.getInstance();
        Date dtTime = getTimeFromString(time);
        calendarTime.setTime(dtTime);

        Calendar calDateTime = Calendar.getInstance();
        calDateTime.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH));
        calDateTime.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
        calDateTime.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));

        calDateTime.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
        calDateTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
        calDateTime.set(Calendar.SECOND, 0);
        calDateTime.set(Calendar.MILLISECOND, 0);


        return calDateTime.getTime();
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            long diff = d2.getTime() - d1.getTime();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
        return -1;
    }

    /**
     * Function used to convert date from one format to another.
     * @param strDate - Date which needs to be converted
     * @param currentFormat - Current format of the date
     * @param requiredFormat - Required format of the date
     * @return - Required formatted date.
     */
    public static String getFormattedDate(String strDate, DateFormat currentFormat, DateFormat requiredFormat) {
        String strUTCDate = null;
        try {
            Date date = currentFormat.parse(strDate);
            strUTCDate = requiredFormat.format(date.getTime());
        } catch (Exception e) {

            e.printStackTrace();
        }
        return strUTCDate;
    }

    /**
     * Function to get calendar instance from given date.
     * @param date - Date from which date of month needs to be extracted.
     * @param dateFormat - Format of the input date.
     * @return Calendar instance from given date.
     */
    private static Calendar getCalendarByDate(String date, DateFormat dateFormat) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    /**
     * Function to get week index by day from given date. This is required by the SDK.
     * @param date - Date from which date of month needs to be extracted.
     * @param dateFormat - Format of the input date.
     * @return Week index of the day from given date
     */

    /**
     * @author - vishalb
     * Function to get day of month from given date
     * @param date - Date from which date of month needs to be extracted.
     * @param dateFormat - Format of the input date.
     * @return - Date of the month.
     */
    public static int getMandateDayOfMonthIndex(String date, DateFormat dateFormat) {
        Calendar cal = getCalendarByDate(date, dateFormat);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}