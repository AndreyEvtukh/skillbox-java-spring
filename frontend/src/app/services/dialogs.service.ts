import { inject, Injectable } from "@angular/core";
import RegisterDialogComponent from '../components/dialogs/register/register-dialog';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogController } from '../components/dialogs/info/info';
import AddUserDialogComponent from '../components/dialogs/add-user/add-user-dialog';
import EditUserDialogComponent from '../components/dialogs/edit-user/edit-user-dialog';
import { ConfirmationDialogController } from '../components/dialogs/confirmation/confirmation';

@Injectable({
  providedIn: "root",
})
export class DialogsService {
  protected readonly dialog = inject(MatDialog);

  public showNoPermissionsDialog() {
    return this.dialog.open(InfoDialogController, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false,
      data: {
        title: "User restrictions",
        message: "You do not have the required permissions to perform this action.",
        confirm: "OK",
        onConfirm: () => null
      }
    });
  }

  public showConfirmationDialog(data: any) {
    return this.dialog
      .open(ConfirmationDialogController, {
        width: '400px',
        maxWidth: '95vw',
        panelClass: 'confirmatiob-dialog-panel',
        closeOnNavigation: false,
        data: {
          message: data.message,
          onConfirm: () => data.onConfirm()
        }
      });
  }

  public showAddUserDialog() {
    return this.dialog.open(AddUserDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false
    });
  }

  public showEditUserDialog(data: any) {
    return this.dialog.open(EditUserDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
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
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false,
    });
  }
}
