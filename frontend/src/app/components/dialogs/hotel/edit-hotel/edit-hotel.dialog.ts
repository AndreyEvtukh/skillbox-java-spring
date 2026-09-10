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
  selector: 'app-edit-room-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MarkControlOnBlurDirective, ErrorToastController],
  templateUrl: `edit-hotel.dialog.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export default class EditHotelDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof EditHotelDialogComponent = EditHotelDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<EditHotelDialogComponent>, { optional: true });

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  public ngOnInit(): void {
    this.form = new FormGroup({
      name: new FormControl(this.data.name, [
        Validators.required,
        Validators.minLength(1),
      ]),
      title: new FormControl(this.data.title, [
        Validators.required,
        Validators.minLength(1),
      ]),
      city: new FormControl(this.data.city, [
        Validators.required,
        Validators.minLength(1),
      ]),
      address: new FormControl(this.data.address, [
        Validators.required,
        Validators.minLength(1),
      ]),
      distance: new FormControl(this.data.distance, [
        Validators.required,
        Validators.minLength(1),
      ])
    });
  }

  protected add(): void {
    const id = this.data.id;
    const { name, title, city, address, distance } = this.form.getRawValue();

    this.hotelsService.update({
      id,
      name,
      title,
      city,
      address,
      distance
    }).subscribe({
      next: res => {
        console.log('[UPDATE Hotel]:', res);

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
