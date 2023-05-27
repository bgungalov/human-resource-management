import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';

/**
 * Service for calendar/dateTime operations.
 */
@Injectable({
  providedIn: 'root',
})
export class CalendarService {
  pipe = new DatePipe('en-US');
  constructor() {}

  /**
   * Format provided date to custom format string.
   * @param date current date to format.
   * @returns formatted date to string.
   */
  formatDateMMDDYYYY(date: Date): string {
    return this.pipe.transform(date, 'MM/dd/yyyy');
  }

  /**
   * Get the day before provided date.
   * @param date provided date.
   * @returns the day before provided date to Date.
   */
  getPreviousDay(date = new Date()): Date {
    const previous = new Date(date.getTime());
    previous.setDate(date.getDate() - 1);

    return previous;
  }

  /**
   * Get today's date.
   * @returns today's date to Date
   */
  getToday(): Date {
    return new Date();
  }

  /**
   * Format string date to date.
   * @param date provided string date.
   * @returns formatted date to Date.
   */
  formatStringToDateTime(date: string): Date {
    const reformatedDate = new Date(date);

    return reformatedDate;
  }

  /**
   * Check if date is valid from date expression.
   * @param dateExpression provided date expression.
   * @returns date expression if valid.
   * @returns Error if date expression is invalid.
   */
  getDate(dateExpression) {
    let possibleDate = new Date(dateExpression);
    if (Object.prototype.toString.call(possibleDate) === '[object Date]')
      if (isNaN(possibleDate.valueOf())) {
        throw new Error('Invalid date expression: ' + dateExpression);
      } else {
        return possibleDate;
      }

    throw new Error('Invalid date expression: ' + dateExpression);
  }
}
