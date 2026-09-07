import { computed, Directive, inject, OnDestroy, signal, Signal, WritableSignal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from "@angular/material/dialog";
import { ConfirmationDialogConfig, ICONS } from '../../app.constants';
import { AuthService } from '../../services/auth-service';
import { FormGroup } from '@angular/forms';

@Directive()
export abstract class ApplicationDialogClass implements OnDestroy {
  protected abstract component: any;
  protected showPassword = false;
  protected showConfirmPassword = false;

  protected readonly data: any  = inject(MAT_DIALOG_DATA);
  protected readonly dialog = inject(MatDialog);
  protected readonly dialogRef: MatDialogRef<any> | null = null;

  protected readonly authService = inject(AuthService);

  protected readonly user: WritableSignal<any> = this.authService.user;
  protected readonly error: WritableSignal<any> = this.authService.error;
  protected readonly info: WritableSignal<any> = signal<any>(null);

  protected readonly ICONS = ICONS;
  protected readonly emailRegExp = new RegExp("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$");
  protected readonly passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?`~]{8,}$/;

  protected form!: FormGroup;

  protected FORM_FIELD = {
    USERNAME: "userName",
    EMAIL: "email",
    PASSWORD: "password",
    CONFIRM_PASSWORD: "confirmPassword",
  } as const;

  protected config: Signal<ConfirmationDialogConfig> = computed(() => {
    const config: ConfirmationDialogConfig = {...this.data}
    if (!config.title) config.title = 'Confirmation';
    if (!config.confirm) config.confirm = 'Confirm';
    if (!config.cancel) config.cancel = 'Cancel';
    if (typeof this.data.onSuccess === 'function') config.onConfirm = this.data.onSuccess;
    if (typeof this.data.onConfirm === 'function') config.onConfirm = this.data.onConfirm;
    if (typeof this.data.onCancel === 'function') config.onCancel = this.data.onCancel ?? (() => null);
    return config;
  });

  protected close(): void {
    if (this.dialogRef) {
      this.error.set(null)
      this.dialogRef.close();
    }
  }

  protected async confirm(): Promise<void> {
    if (typeof this.config().onConfirm === 'function') {
      this.config().onConfirm?.();
    }
    this.close();
  }

  protected setInfo(data: any) {
    this.info.set(data);
  }

  protected removeInfo() {
    this.info.set(false);
  }

  ngOnDestroy() {
    this.dialogRef?.close('destroyed');
  }
}
