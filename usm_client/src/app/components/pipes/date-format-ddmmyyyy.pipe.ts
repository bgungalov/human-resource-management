import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateFormatDDMMYYYY',
})
export class DateFormatDDMMYYYYPipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): unknown {
    const datePipe = new DatePipe('en-GB');
    value = datePipe.transform(value, 'dd/MM/yyyy HH:mm:ss');
    return value;
  }
}
