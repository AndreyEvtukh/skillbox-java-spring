import { Component, computed, inject, Signal, WritableSignal } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../services/auth.service';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { AUTH, ICONS, STATISTICS_DOC_TYPE } from '../../app.constants';
import { MatMenu, MatMenuTrigger } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NgClass } from '@angular/common';
import { DialogsService } from '../../services/dialogs.service';
import { User } from '../../services/users.service';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';

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
  templateUrl: './app.header.html',
  animations: [AUTH.FADE_ANIMATION],
})
export default class appHeaderController {
  private readonly http = inject(HttpClient);
  private readonly url = `http://localhost:8082/api/v1`;

  private readonly router = inject(Router);
  protected readonly dialog = inject(MatDialog);
  private readonly authService = inject(AuthService);
  protected readonly dialogsService: DialogsService = inject(DialogsService);

  protected readonly user: WritableSignal<User | null> = this.authService.user;
  protected readonly loading: WritableSignal<boolean> = this.authService.waitLoginSpinner;

  protected readonly isAdmin: Signal<boolean> = computed(() => this.user()?.role === 'ADMIN');

  public goLogin() {
    this.dialogsService.showLoginialog();
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

  async download(docType: 'csv' | 'pdf'): Promise<void> {
    try {
      const response = await firstValueFrom(
        this.http.get(`${this.url}/statistics/${docType}`, {
          responseType: 'blob',
          observe: 'response'
        })
      );

      if (!response.body) return;

      const contentDisposition = response.headers.get('Content-Disposition');
      const fileName = contentDisposition?.match(/filename="?([^"]+)"?/)?.[1] ?? `statistics.${docType}`;
      const url = URL.createObjectURL(response.body);
      const link = document.createElement('a');
      link.href = url;
      link.download = fileName;

      document.body.appendChild(link);
      link.click();
      link.remove();

      URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Failed to download statistics:', error);
    }
  }

  protected readonly ICONS = ICONS;
  protected readonly STATISTICS_DOC_TYPE = STATISTICS_DOC_TYPE;
}

