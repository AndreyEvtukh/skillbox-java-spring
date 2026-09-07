import { Directive } from '@angular/core';
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator
} from '@angular/forms';

@Directive({
  selector: '[appNoWhitespace]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: NoWhitespaceDirective,
      multi: true
    }
  ]
})
export class NoWhitespaceDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value as string | null;

    if (!value) {
      return null;
    }

    return /\s/.test(value)
      ? {whitespace: true}
      : null;
  }
}
