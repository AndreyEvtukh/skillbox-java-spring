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

@Injectable({
  providedIn: "root",
})
export class DialogsService {
  protected readonly dialog = inject(MatDialog);

  public showNoPermissionsDialog() {
    return this.dialog.open(InfoDialogController, {
      width: '420px',
      maxWidth: '95vw',
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
        width: '400px',
        maxWidth: '95vw',
        closeOnNavigation: false,
        data: {
          ...data
        }
      });
  }

  // === User START ===//
  public showAddUserDialog() {
    return this.dialog.open(AddUserDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      closeOnNavigation: false
    });
  }

  public showEditUserDialog(data: any) {
    return this.dialog.open(EditUserDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      closeOnNavigation: false,
      data: {
        ...data.user
      }
    });
  }

  public showRegisterDialog() {
    return this.dialog.open(RegisterDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      closeOnNavigation: false,
    });
  }

  // === Hotel START ===//
  public showAddHotelDialog() {
    return this.dialog.open(AddHotelDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      closeOnNavigation: false
    });
  }

  public showEditHotelDialog(data: any) {
    return this.dialog.open(EditHotelDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      closeOnNavigation: false,
      data: {
        ...data.hotel
      }
    });
  }
}
