/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dat
 */
public class DateTimeHelper {
//     public static Date getWeekStart(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - calendar.getFirstDayOfWeek();
//        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
//        return calendar.getTime();
//    }

    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set ngày đầu tiên của tuần là Thứ Hai
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int difference = calendar.getFirstDayOfWeek() - dayOfWeek;

        // Nếu ngày đầu tiên của tuần không phải là Thứ Hai, điều chỉnh lại ngày đầu tiên
        if (difference > 0) {
            difference -= 7;
        }

        // Trừ đi số ngày để đạt được ngày bắt đầu của tuần
        calendar.add(Calendar.DAY_OF_MONTH, difference);

        // Đặt giờ, phút, giây và mili-giây về 0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return new Date(calendar.getTimeInMillis());
    }

    public static java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utilDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return new java.sql.Date(calendar.getTimeInMillis());
        } else {
            return null;
        }
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static ArrayList<java.sql.Date> getListBetween(Date from, Date to) {
        ArrayList<java.sql.Date> dates = new ArrayList<>();
        Date temp = from;
        while (!temp.after(to)) {
            dates.add(convertUtilDateToSqlDate(temp));
            temp = addDaysToDate(temp, 1);
        }
        return dates;
    }

    public java.util.Date convertSqlDateToUtilDate(java.sql.Date sqlDate) {
        return sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null;
    }
}