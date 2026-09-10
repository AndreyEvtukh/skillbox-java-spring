import { Directive, HostListener } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appMarkControlOnBlur]',
  standalone: true
})
export class MarkControlOnBlurDirective {

  constructor(private readonly ngControl: NgControl) {}

  @HostListener('blur')
  onBlur(): void {
    this.ngControl.control?.markAsTouched();
    this.ngControl.control?.markAsDirty();
  }
}
