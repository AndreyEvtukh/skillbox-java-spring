import { Component, computed, effect, inject, Signal, signal, WritableSignal } from '@angular/core';
import { MatDialog, MatDialogActions, MatDialogRef } from "@angular/material/dialog";
import { MatFormField, MatInputModule, MatLabel } from "@angular/material/input";
import {
  FormControl,
  FormGroup, FormsModule,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { NgClass } from "@angular/common";
import { AUTH, ICONS } from '../../../app.constants';
import { MatchPasswordDirective } from '../../../directives/match-password.directive';
import LoginDialogComponent from '../login/login-dialog';
import { NoWhitespaceDirective } from '../../../directives/no-whitespace.directive';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-register',
  imports: [
    MatIcon,
    MatDialogActions, MatLabel, MatFormField,
    MatInputModule, MatFormFieldModule, ReactiveFormsModule,
    FormsModule, MatchPasswordDirective, MatProgressSpinnerModule,
    NgClass, NoWhitespaceDirective],
  templateUrl: `register-dialog.html`,
  styleUrl: 'register-dialog.css',
  animations: [AUTH.STATUS_ANIMATION],
})
export default class RegisterDialogComponent {
  protected dialog = inject(MatDialog);
  private readonly authService = inject(AuthService);

  protected showPassword = false;
  protected showConfirmPassword = false;
  protected FORM_FIELD = {
    USERNAME: "userName",
    EMAIL: "email",
    PASSWORD: "password",
    CONFIRM_PASSWORD: "confirmPassword",
  } as const;

  private dialogRef = inject(MatDialogRef<RegisterDialogComponent>);

  private emailRegExp = new RegExp("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$");
  private passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?`~]{8,}$/;

  protected form: FormGroup = new FormGroup({
    [this.FORM_FIELD.USERNAME]: new FormControl("", [
      Validators.required,
      Validators.minLength(1),
      Validators.maxLength(100),
    ]),
    [this.FORM_FIELD.PASSWORD]: new FormControl("", [
      Validators.required,
      Validators.minLength(1),
    ]),
    [this.FORM_FIELD.CONFIRM_PASSWORD]: new FormControl("", [
      Validators.required,
      Validators.minLength(1),
    ]),
    [this.FORM_FIELD.EMAIL]: new FormControl("", [
      Validators.pattern(this.emailRegExp),
      Validators.nullValidator,
      Validators.required,
      Validators.email,
    ])
  });

  protected user: WritableSignal<any> = signal<any>(null);
  protected readonly loading: WritableSignal<boolean> = this.authService.waitLoginSpinner;
  protected response: WritableSignal<any> = signal<any>({});
  protected responseMsg: WritableSignal<boolean> = signal<boolean>(false);
  protected formSignal = signal({
    [this.FORM_FIELD.USERNAME]: '',
    [this.FORM_FIELD.EMAIL]: '',
    [this.FORM_FIELD.PASSWORD]: '',
    [this.FORM_FIELD.CONFIRM_PASSWORD]: ''
  });

  protected isFormEmpty: Signal<boolean> = computed(() => {
    const f = this.formSignal();
    return Object.keys(f).some(key => !key.trim().length);
  });

  get emailIsInvalid() {
    const {errors} = this.form.controls["email"];
    return errors?.["email"] || errors?.["pattern"];
  }

  constructor() {
    effect(() => {
      const user = this.authService.user();
      console.error(user)
      this.user.set(user);

      if (user && 'ok' in user && !user.ok) {
        this.responseMsg.set(true);
        this.response.set({...this.user()});
      }

      if (user && 'email' in user && user.email) {
        this.close();
      }
    });
  }

  protected goLogin = async (): Promise<void> => {
    this.dialog.open(LoginDialogComponent, {
      width: '420px',
      maxWidth: '95vw',
      panelClass: 'auth-dialog-panel',
      closeOnNavigation: false,
      data: {}
    });

    if (this.dialogRef) this.dialogRef.close('switch');
  }

  protected register = () => {
    const email = this.form.controls[this.FORM_FIELD.EMAIL].value;
    const username = this.form.controls[this.FORM_FIELD.USERNAME].value;
    const password = this.form.controls[this.FORM_FIELD.PASSWORD].value;

    this.authService.register(email, username, password);

    this.responseMsg.set(false);

  }

  protected close = () => this.dialogRef.close();

  protected doBlurredInput(input: string) {
    this.form.controls[input].markAsTouched();
    this.form.controls[input].markAsDirty();
  }

  protected dismissResponseMsg() {
    this.responseMsg.set(false);
  }

  protected readonly ICONS = ICONS;
}
