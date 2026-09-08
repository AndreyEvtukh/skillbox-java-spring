import { Component, inject, WritableSignal } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import LoginDialogComponent from '../dialogs/login/login-dialog';
import { AuthService } from '../../services/auth.service';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { AUTH, ICONS } from '../../app.constants';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { ConfirmationDialogController } from '../dialogs/confirmation/confirmation';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NgClass } from '@angular/common';
import { DialogsService } from '../../services/dialogs.service';
import { User } from '../../services/users.service';

@Component({
  imports: [
    MatProgressSpinner,
    MatMenu,
    MatMenuTrigger,
    MatIcon,
    RouterLink,
    RouterLinkActive,
    NgClass
  ],
  selector: 'app-header',
  styleUrl: './app.header.css',
  templateUrl: './app.header.html',
  animations: [AUTH.FADE_ANIMATION],
})
export default class appHeaderController {
  private readonly router = inject(Router);
  protected ref: MatDialogRef<LoginDialogComponent | ConfirmationDialogController> | null = null;
  protected dialog = inject(MatDialog);
  private readonly authService = inject(AuthService);
  protected dialogsService: DialogsService = inject(DialogsService);

  user: WritableSignal<User | null> = this.authService.user;
  loading: WritableSignal<boolean> = this.authService.waitLoginSpinner;

  public goLogin() {
    this.dialog.open(LoginDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false
    });
  }

  public goLogout() {
    const config = {
      message: "Are you sure you want to logout?",
      onConfirm: () => this.authService.logout()
    }
    this.dialogsService.showConfirmationDialog(config);
  }

  isPageActive(page: string): boolean {
    if (this.router.url === "/") {
      return page === `main`;
    }
    return this.router.url === `/${page}`;
  }

  protected readonly ICONS = ICONS;
}

