import { Component, inject, OnInit, WritableSignal } from '@angular/core';
import { MatDialogActions, MatDialogRef } from "@angular/material/dialog";
import { MatFormField, MatInputModule, MatLabel } from "@angular/material/input";
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { AUTH } from '../../../../app.constants';
import { ApplicationDialogClass } from '../../app.dialog.class';
import { MarkControlOnBlurDirective } from '../../../../directives/mark-control-on-blur.directive';
import { ErrorToastController } from '../../error-toast/error-toast';

@Component({
  selector: 'app-add-room-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MarkControlOnBlurDirective, ErrorToastController],
  templateUrl: `add-hotel.dialog.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export default class AddHotelDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof AddHotelDialogComponent = AddHotelDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<AddHotelDialogComponent>, { optional: true });

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  public ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      title: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      city: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      address: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      distance: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ])
    });
  }

  protected add(): void {
    const { name, title, city, address, distance } = this.form.getRawValue();

    this.hotelsService.add({
      name,
      title,
      city,
      address,
      distance
    }).subscribe({
      next: res => {
        console.log('[ADD Hotel]:', res);

        // this.removeInfo();
        this.close({ ok: true });
      },

      error: error => {
        console.error(error)

        const message = error.error?.message || 'Failed to add hotel';
        this.error.set(message);
      }
    });
  }
}
