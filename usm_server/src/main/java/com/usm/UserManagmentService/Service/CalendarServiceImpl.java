package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Constants.TYPES_OF_DATE_OFFSET;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * > This class is a service that implements the `CalendarService` interface
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    private SimpleDateFormat dateTimeFormatter;

    /**
     * It returns the current date with the time set to midnight
     *
     * @return The current date.
     */
    @Override
    public Date getCurrentDateHoursToMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }

    /**
     * It takes a year as an argument and returns a Date object that represents the last day of that year.
     *
     * @param year The year you want to set the end date to.
     * @return A date object.
     */
    @Override
    public Date setDateToLastDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }


    /**
     * It takes a string in the format of MM/dd/yyyy and returns a Date object.
     *
     * @param dateExpression The date expression to be parsed.
     * @return A Date object.
     */
    @Override
    public Date generateDateFromDDMMYYYY(String dateExpression) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(dateExpression);
    }

    /**
     * Convert a Date object to a LocalDateTime object.
     *
     * @param date The date to convert.
     * @return A LocalDateTime object.
     */
    @Override
    public LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * It takes a date, formats it as a string, and returns it.
     *
     * @param date The date to format
     * @return A string representation of the date.
     */
    @Override
    public String formatDateToStringMMddyyyy(Date date) {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    /**
     * If the offset is positive, increment the date by the offset, else decrement the date by the offset.
     *
     * @param date   The date to be offset.
     * @param offset The amount of time to offset the date by.
     * @param type   This is the type of offset you want to apply to the date.
     * @return A date object.
     */
    @Override
    public Date offsetDateTime(Date date, long offset, TYPES_OF_DATE_OFFSET type) {

        Date offsetDate = null;

        if (offset >= 0) {
            offsetDate = Date.from(incrementDate(date, offset, type).toInstant());
        } else {
            offsetDate = Date.from(decrementDate(date, offset, type).toInstant());
        }
        return offsetDate;
    }

    /**
     * It takes a date, sets the hour to 23, the minute to 59, and the second to 59, and returns the date.
     *
     * @param date The date to be modified
     * @return The last hour of the day.
     */
    @Override
    public Date setLastHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    /**
     * It takes a date, an offset, and a type of offset. It then converts the date to an
     * OffsetDateTime object, and then uses a switch statement to increment the date by the given offset.
     *
     * @param date   The date to be incremented.
     * @param offset The number of days, months, or years to increment the date by.
     * @param type   The type of date offset you want to increment by.
     * @return A new OffsetDateTime object with the offset applied.
     */
    private OffsetDateTime incrementDate(Date date, long offset, TYPES_OF_DATE_OFFSET type) {

        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), ZoneOffset.UTC);

        return switch (type) {
            case DAY -> offsetDateTime.plusDays(offset);
            case MONTH -> offsetDateTime.plusMonths(offset);
            case YEAR -> offsetDateTime.plusYears(offset);
            default -> null;
        };
    }

    /**
     * It takes a date, an offset, and a type of offset (day, month, or year) and returns a new date with the offset
     * applied
     *
     * @param date   The date to be decremented
     * @param offset The number of days, months, or years to offset the date by.
     * @param type   The type of date offset you want to use.
     * @return A date that is offset by the number of days, months, or years.
     */
    private OffsetDateTime decrementDate(Date date, long offset, TYPES_OF_DATE_OFFSET type) {

        OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), ZoneOffset.UTC);

        return switch (type) {
            case DAY -> offsetDateTime.minusDays(Math.abs(offset));
            case MONTH -> offsetDateTime.minusMonths(Math.abs(offset));
            case YEAR -> offsetDateTime.minusYears(Math.abs(offset));
            default -> null;
        };
    }
}
