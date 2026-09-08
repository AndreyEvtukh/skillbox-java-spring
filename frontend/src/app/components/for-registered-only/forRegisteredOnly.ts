import { Component, inject, } from '@angular/core';
import LoginDialogComponent from '../dialogs/login/login-dialog';
import { MatDialog } from '@angular/material/dialog';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  imports: [
    RouterLinkActive,
    RouterLink
  ],
  selector: 'app-for-registered-only-page',
  styleUrl: './forRegisteredOnly.css',
  templateUrl: './forRegisteredOnly.html',
})

export default class ForRegisteredOnlyController {
  protected dialog = inject(MatDialog);
  protected router = inject(Router);

  public goMain() {
    this.dialog.open(LoginDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false
    });
  }

  public goLogin() {
    this.dialog.open(LoginDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false
    });
  }
}

