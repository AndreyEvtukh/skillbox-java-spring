import { inject, Injectable } from "@angular/core";
import RegisterDialogComponent from '../components/dialogs/register/register-dialog';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogController } from '../components/dialogs/info/info';
import AddUserDialogComponent from '../components/dialogs/user/add-user/add-user.dialog';
import EditUserDialogComponent from '../components/dialogs/user/edit-user/edit-user.dialog';
import { ConfirmationDialogController } from '../components/dialogs/confirmation/confirmation';
import AddHotelDialogComponent from '../components/dialogs/hotel/add-hotel/add-hotel.dialog';
import { ConfirmationDialogConfig } from '../app.constants';
import EditHotelDialogComponent from '../components/dialogs/hotel/edit-hotel/edit-hotel.dialog';
import AddRoomDialogComponent from '../components/dialogs/room/add-room/add-room.dialog';
import EditRoomDialogComponent from '../components/dialogs/room/edit-room/edit-room.dialog';
import AddBookingDialogComponent from '../components/dialogs/booking/add-booking/add-booking.dialog';
import LoginDialogComponent from '../components/dialogs/login/login-dialog';

@Injectable({
  providedIn: "root",
})
export class DialogsService {
  protected readonly dialog = inject(MatDialog);
  private readonly defaultDialogSize = {
    width: '420px',
    maxWidth: '95vw'
  };

  public showNoPermissionsDialog() {
    return this.dialog.open(InfoDialogController, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
      data: {
        title: "User restrictions",
        message: "You do not have the required permissions to perform this action.",
        confirmButtonText: "OK",
        onConfirm: () => null
      }
    });
  }

  public showConfirmationDialog(data: ConfirmationDialogConfig) {
    return this.dialog
      .open(ConfirmationDialogController, {
        ...this.defaultDialogSize,
        closeOnNavigation: false,
        data: {
          ...data
        }
      });
  }

  // === User START ===//
  public showAddUserDialog() {
    return this.dialog.open(AddUserDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false
    });
  }

  public showEditUserDialog(data: any) {
    return this.dialog.open(EditUserDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
      data: {
        ...data.user
      }
    });
  }

  public showRegisterDialog() {
    return this.dialog.open(RegisterDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
    });
  }


  public showLoginialog() {
    return this.dialog.open(LoginDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
    });
  }

  // === Hotel START ===//
  public showAddHotelDialog() {
    return this.dialog.open(AddHotelDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false
    });
  }

  public showEditHotelDialog(data: any) {
    return this.dialog.open(EditHotelDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
      data: {
        ...data.hotel
      }
    });
  }

  // === Room START ===//
  public showAddRoomDialog() {
    return this.dialog.open(AddRoomDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false
    });
  }

  public showEditRoomDialog(data: any) {
    return this.dialog.open(EditRoomDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false,
      data: {
        ...data.room
      }
    });
  }

  // === Booking START ===//
  public showAddBookingDialog() {
    return this.dialog.open(AddBookingDialogComponent, {
      ...this.defaultDialogSize,
      closeOnNavigation: false
    });
  }
}
