import { computed, Directive, effect, inject, OnDestroy, signal, Signal, WritableSignal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from "@angular/material/dialog";
import { ConfirmationDialogConfig, ICONS } from '../../app.constants';
import { AuthService } from '../../services/auth.service';
import { FormGroup } from '@angular/forms';
import { isObservable } from 'rxjs';

@Directive()
export abstract class ApplicationDialogClass implements OnDestroy {
  protected readonly data: any  = inject(MAT_DIALOG_DATA);
  protected readonly dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<any>, { optional: true });
  protected readonly dialog = inject(MatDialog);
  protected readonly authService = inject(AuthService);

  protected abstract component: any;
  protected showPassword = false;
  protected showConfirmPassword = false;

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
    if (!config.confirmButtonText) config.confirmButtonText = 'Confirm';
    if (!config.cancelButtonText) config.cancelButtonText = 'Cancel';
    if (typeof this.data.onSuccess === 'function') config.onConfirm = this.data.onSuccess;
    if (typeof this.data.onConfirm === 'function') config.onConfirm = this.data.onConfirm;
    if (typeof this.data.onCancel === 'function') config.onCancel = this.data.onCancel ?? (() => null);
    return config;
  });

  get emailIsInvalid() {
    const { errors } = this.form.controls["email"];
    return errors?.["email"] || errors?.["pattern"];
  }

  constructor() {
    effect(() => {
      const user = this.authService.user();
      this.user.set(user);

      if (this.error()) {
        this.setInfo({ ok: false, message: this.error() });
      }
    });
  }

  protected close(result?: {ok : boolean}): void {
    if (this.dialogRef) {
      this.error.set(null)
      this.dialogRef.close(result);
    }
  }

  protected confirm(): void {
    const onConfirm = this.config().onConfirm;

    if (typeof onConfirm !== 'function') {
      this.dialogRef!.close({ ok: true });
      return;
    }

    const result = onConfirm();

    if (isObservable(result)) {
      result.subscribe({
        next: () => {
          this.dialogRef?.close({ ok: true });
        },
        error: error => {
          console.error(error);
        }
      });

      return;
    }

    this.dialogRef?.close({ ok: true });
  }

  protected setInfo(data: any) {
    this.info.set(data);
  }

  protected removeInfo() {
    this.info.set(false);
  }

  ngOnDestroy() {
    // this.dialogRef?.close('destroyed');
  }
}
