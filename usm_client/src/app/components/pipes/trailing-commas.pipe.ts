import { Pipe, PipeTransform } from '@angular/core';

/* The TrailingCommasPipe class implements the PipeTransform interface's transform() method that
accepts a value and an optional array of parameters and returns a value. */
@Pipe({
  name: 'trailingCommas',
})
export class TrailingCommasPipe implements PipeTransform {
  transform(value: unknown, ...args: unknown[]): unknown {
    return value ? `${value}, ` : '';
  }
}
