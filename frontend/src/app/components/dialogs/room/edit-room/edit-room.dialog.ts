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
import { MatOption, MatSelect } from '@angular/material/select';

@Component({
  selector: 'app-edit-room-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MarkControlOnBlurDirective, MatSelect, MatOption],
  templateUrl: `edit-room.dialog.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export default class EditRoomDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof EditRoomDialogComponent = EditRoomDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<EditRoomDialogComponent>, { optional: true });

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  public ngOnInit(): void {
    this.form = new FormGroup({
      category: new FormControl(this.data.name, [
        Validators.required,
        Validators.minLength(1),
      ]),
      number: new FormControl(this.data.number, [
        Validators.required,
        Validators.minLength(1),
      ]),
      price: new FormControl(this.data.price, [
        Validators.required,
        Validators.minLength(1),
      ]),
      maxCapacity: new FormControl(this.data.maxCapacity, [
        Validators.required,
        Validators.minLength(1),
      ]),
      description: new FormControl(this.data.description, [
        Validators.required,
        Validators.minLength(1),
      ]),
      hotelId: new FormControl(this.data.hotelId, [
        Validators.required,
        Validators.minLength(1),
      ])
    });
  }

  protected edit(): void {
    const id = this.data.id;
    const { category: name, number, price, maxCapacity, description, hotelId } = this.form.getRawValue();

    this.roomsService.update({ id, name, number, price, maxCapacity, description, hotelId }).subscribe({
      next: res => {
        console.log('[UPDATE Room]:', res);

        this.removeInfo();
        this.close({ ok: true });
      },

      error: error => {
        console.error(error)

        const message = error.error?.message || 'Failed to add room';
        this.setInfo({ ok: false, message });
      }
    });
  }
}
