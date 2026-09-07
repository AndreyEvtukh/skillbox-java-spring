import { Component, inject, } from '@angular/core';
import LoginDialogComponent from '../dialogs/login/login-dialog';
import { MatDialog } from '@angular/material/dialog';

@Component({
  imports: [],
  selector: 'app-for-registered-only-page',
  styleUrl: './forRegisteredOnly.css',
  templateUrl: './forRegisteredOnly.html',
})

export default class ForRegisteredOnlyController {
  protected dialog = inject(MatDialog);

  public goLogin() {
    this.dialog.open(LoginDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false
    });
  }
}

