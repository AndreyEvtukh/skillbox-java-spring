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
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { Room } from '../../../../services/rooms.service';
import { NgClass } from '@angular/common';
import { MatDatepicker, MatDatepickerInput, MatDatepickerToggle } from '@angular/material/datepicker';
import { BookingService } from '../../../../services/bokking.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-add-booking-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MarkControlOnBlurDirective, MatOption, MatSelect, NgClass, MatDatepickerInput, MatDatepickerToggle, MatDatepicker],
  templateUrl: `add-booking.dialog.html`,
  animations: [AUTH.STATUS_ANIMATION]
})
export default class AddBookingDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof AddBookingDialogComponent = AddBookingDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<AddBookingDialogComponent>, { optional: true });

  protected readonly bookingService: BookingService = inject(BookingService);

  protected availableRooms: Room[] = [];
  protected availableCategories: { id: string }[] = [];
  protected availableRoomNumbers: { id: number }[] = [];

  protected readonly today = new Date();

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  protected get minCheckOutDate(): Date {
    const checkIn = this.form.controls['checkIn'].value;

    if (!checkIn) {
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      return today;
    }

    const minDate = new Date(checkIn);
    minDate.setDate(minDate.getDate() + 1);

    return minDate;
  }

  public ngOnInit(): void {
    this.today.setHours(0, 0, 0, 0);

    this.form = new FormGroup({
      user: new FormControl("", Validators.required),
      hotel: new FormControl("", Validators.required),
      category: new FormControl(
        { value: "", disabled: true },
        Validators.required
      ),
      number: new FormControl(
        { value: "", disabled: true },
        Validators.required
      ),
      checkIn: new FormControl(
        { value: "", disabled: true },
        Validators.required
      ),
      checkOut: new FormControl(
        { value: "", disabled: true },
        Validators.required
      )
    });
  }

  protected onHotelSet(hotelId: string): void {
    this.availableRooms = this.rooms()
      .filter(room => room.hotelId === hotelId);

    this.availableCategories = [
      ...new Set(this.availableRooms.map(room => room.name))
    ].map(name => ({ id: name }));

    this.form.controls['category'].reset();
    this.form.controls['number'].disable();
    this.form.controls['checkIn'].disable();
    this.form.controls['checkOut'].disable();

    if (hotelId) {
      this.form.controls['category'].enable();
    } else {
      this.form.controls['category'].disable();
    }

    this.availableRoomNumbers = [];
  }

  protected onCategorySet(categoryId: string): void {
    this.availableRoomNumbers = this.availableRooms
      .filter(room => room.name === categoryId)
      .map(room => ({ id: room.number }));

    this.form.controls['number'].reset();

    if (categoryId) {
      this.form.controls['number'].enable();
    } else {
      this.form.controls['number'].disable();
    }
  }

  protected onNumberSet(): void {
    this.form.controls['checkIn'].enable();
    this.form.controls['checkOut'].enable();
  }

  protected async add(): Promise<void> {
    const formValue = this.form.getRawValue();
    const userId: string = formValue.user
    const hotelId: string = this.hotels().find(hotel => hotel.id === formValue.hotel)!.id!;
    const roomId: string = this.rooms().find(room => room.hotelId === hotelId)!.id!;
    const checkIn: string = this.formatDate(formValue.checkIn);
    const checkOut: string = this.formatDate(formValue.checkOut);

    const result = await firstValueFrom(this.bookingService.add({ userId, roomId, checkIn, checkOut }));
    if (result?.ok) {
      this.removeInfo();
      this.close(result);
    } else {
      const message = 'Failed to book';
      this.setInfo({ ok: false, message });
    }
  }

  private formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  // const { name, title, city, address, distance } = this.form.getRawValue();

  //   this.hotelsService.add({
  //     name,
  //     title,
  //     city,
  //     address,
  //     distance
  //   }).subscribe({
  //     next: res => {
  //       console.log('[ADD Hotel]:', res);
  //
  //       this.removeInfo();
  //       this.close({ ok: true });
  //     },
  //
  //     error: error => {
  //       console.error(error)
  //
  //       const message = error.error?.message || 'Failed to add hotel';
  //       this.setInfo({ ok: false, message });
  //     }
  //   });
  // }
}
