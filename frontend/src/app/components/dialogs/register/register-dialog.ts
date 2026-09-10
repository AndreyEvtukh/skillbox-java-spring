import { Component, computed, effect, inject, Signal, signal, WritableSignal } from '@angular/core';
import { MatDialogActions, MatDialogRef } from "@angular/material/dialog";
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
import { AUTH } from '../../../app.constants';
import { MatchPasswordDirective } from '../../../directives/match-password.directive';
import { NoWhitespaceDirective } from '../../../directives/no-whitespace.directive';
import { DialogsService } from '../../../services/dialogs.service';
import { ApplicationDialogClass } from '../app.dialog.class';
import { ErrorToastController } from '../error-toast/error-toast';

@Component({
  selector: 'app-register',
  imports: [
    MatIcon,
    MatDialogActions, MatLabel, MatFormField,
    MatInputModule, MatFormFieldModule, ReactiveFormsModule,
    FormsModule, MatchPasswordDirective, MatProgressSpinnerModule,
    NgClass, NoWhitespaceDirective, ErrorToastController],
  templateUrl: `register-dialog.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export default class RegisterDialogComponent extends ApplicationDialogClass {
  protected component: typeof RegisterDialogComponent = RegisterDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<RegisterDialogComponent>, { optional: true });
  protected readonly dialogsService: DialogsService = inject(DialogsService);

  protected override form: FormGroup = new FormGroup({
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

  constructor() {
    super();

    effect(() => {
      if (this.user()?.email) {
        this.close();
      }
    });
  }

  protected goLogin = async (): Promise<void> => {
    this.dialogsService.showLoginialog();
    if (this.dialogRef) this.dialogRef.close('switch');
  }

  protected register = () => {
    const controls = this.form.controls;
    const email = controls[this.FORM_FIELD.EMAIL].value;
    const username = controls[this.FORM_FIELD.USERNAME].value;
    const password = controls[this.FORM_FIELD.PASSWORD].value;

    this.authService.register(email, username, password);
    this.responseMsg.set(false);
  }

  protected doBlurredInput(input: string) {
    this.form.controls[input].markAsTouched();
    this.form.controls[input].markAsDirty();
  }
}
