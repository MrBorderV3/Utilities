package me.border.utilities.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * Utility class to calculate time differences
 */
public class TimeUtils {

    /**
     * Get the difference in the given {@link ChronoUnit} between an {@link LocalDateTime} and the closest given hour and minute.
     *
     * @param time The time to calculate from
     * @param designatedHour The hour to calculate to
     * @param designatedMinute The minute to calculate to
     * @param timeUnit The unit to return in
     *
     * @return The difference between the times.
     */
    public static long calculateTime(LocalDateTime time, int designatedHour, int designatedMinute, ChronoUnit timeUnit){
        int year = time.getYear();
        Month month = time.getMonth();
        int dayOfMonth = time.getDayOfMonth();
        int hour = time.getHour();
        int minute = time.getMinute();

        LocalDateTime then;
        if (hour > designatedHour || hour == designatedHour && minute >= designatedMinute) {
            boolean leapYear = time.getChronology().isLeapYear(year);
            int lastDayOfYear = leapYear ? 366 : 365;
            int lastDayOfMonth = month.length(leapYear);

            boolean monthOver = dayOfMonth == lastDayOfMonth;

            then = LocalDateTime.of(time.getDayOfYear() == lastDayOfYear ? year+1 : year,
                    monthOver ? month.plus(1) : month,
                    monthOver ? 1 : dayOfMonth+1, designatedHour, designatedMinute);

        } else {
            then = LocalDateTime.of(year, month, dayOfMonth, designatedHour, designatedMinute);
        }
        return time.until(then, timeUnit);
    }

    /**
     * Get the difference in the given {@link ChronoUnit} between a given hour and minute and the closest given hour and minute.
     *
     * @param hour The hour to calculate from
     * @param minute The minute to calculate from
     * @param designatedHour The hour to calculate to
     * @param designatedMinute The minute to calculate to
     * @param timeUnit The unit to return in
     *
     * @return The difference between the times.
     */
    public static long calculateTime(int hour, int minute, int designatedHour, int designatedMinute, ChronoUnit timeUnit){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute);
        return calculateTime(time, designatedHour, designatedMinute, timeUnit);
    }

    /**
     * Get the difference between the current time and the given time in the given {@link ChronoUnit}
     *
     * @param designatedHour The hour to calculate to
     * @param designatedMinute The minute to calculate to
     * @param timeUnit The unit to return in
     *
     * @return The difference between the times.
     */
    public static long calculateTime(int designatedHour, int designatedMinute, ChronoUnit timeUnit){
        LocalDateTime now = LocalDateTime.now();
        return calculateTime(now, designatedHour, designatedMinute, timeUnit);
    }

    /**
     * Get the difference between the current time and the given time in milliseconds
     *
     * @param designatedHour The hour to calculate to
     * @param designatedMinute The minute to calculate to
     *
     * @return The difference between the times.
     */
    public static long calculateTime(int designatedHour, int designatedMinute){
        LocalDateTime now = LocalDateTime.now();
        return calculateTime(now, designatedHour, designatedMinute, ChronoUnit.MILLIS);
    }
}
