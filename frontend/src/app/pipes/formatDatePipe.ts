import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatDate',
  standalone: true
})
export class FormatDatePipe implements PipeTransform {

  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }

    const [year, month, day] = value.split('-');

    const date = new Date(
      Number(year),
      Number(month) - 1,
      Number(day)
    );

    return `${Number(day)} ${date.toLocaleString('en', {
      month: 'short'
    })} ${year}`;
  }
}
