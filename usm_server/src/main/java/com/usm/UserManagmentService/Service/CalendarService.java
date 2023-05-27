package com.usm.UserManagmentService.Service;

import com.usm.UserManagmentService.Constants.TYPES_OF_DATE_OFFSET;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public interface CalendarService {

    /**
     * This function returns the current date.
     *
     * @return A date object.
     */
    Date getCurrentDateHoursToMidnight();

    /**
     * This function takes an integer and returns a Date object.
     *
     * @param year The year to set the end date to.
     * @return The end date of the year.
     */
    Date setDateToLastDayOfYear(int year);

    /**
     * It takes a date expression in the format of DDMMYYYY and returns a Date object
     *
     * @param dateExpression The date expression in the format of dd/MM/yyyy.
     * @return A Date object
     */
    Date generateDateFromDDMMYYYY(String dateExpression) throws ParseException;

    /**
     * Convert a java.util.Date to a java.time.LocalDateTime.
     *
     * @param date The date to convert
     * @return A LocalDateTime object.
     */
    LocalDateTime dateToLocalDateTime(Date date);

    /**
     * Given a date, return a string representation of that date.
     *
     * @param date The date to be formatted.
     * @return A string representation of the date.
     */
    String formatDateToStringMMddyyyy(Date date);

    /**
     * It takes a date and an offset and returns a new date that is offset by the given amount.
     *
     * @param date   The date to be offset
     * @param offset The amount of time to offset the date by.
     * @param type   The type of offset you want to apply.
     * @return A date object with the offset applied.
     */
    Date offsetDateTime(Date date, long offset, TYPES_OF_DATE_OFFSET type);

    /**
     * Given a date, return a new date with the same year, month, and day, but with the last hour of the day.
     *
     * @param date The date to be modified
     * @return A date object with the time set to the last hour of the day.
     */
    Date setLastHourOfDay(Date date);
}
