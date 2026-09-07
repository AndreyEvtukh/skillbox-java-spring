import { Component, effect, inject, OnInit, WritableSignal } from '@angular/core';
import { MatDialogActions, MatDialogRef } from "@angular/material/dialog";
import { MatFormField, MatInputModule, MatLabel } from "@angular/material/input";
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { AUTH } from '../../../app.constants';
import { MatIcon } from '@angular/material/icon';
import { NoWhitespaceDirective } from '../../../directives/no-whitespace.directive';
import { ApplicationDialogClass } from '../app.dialog.class';
import { DialogsService } from '../../../services/dialogs.service';

@Component({
  selector: 'app-login-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MatIcon, NoWhitespaceDirective],
  templateUrl: `login-dialog.html`,
  styleUrl: 'login-dialog.css',
  animations: [AUTH.STATUS_ANIMATION],
})
export default class LoginDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof LoginDialogComponent = LoginDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<LoginDialogComponent>, { optional: true });
  protected dialogsService: DialogsService = inject(DialogsService);

  protected readonly loading: WritableSignal<boolean> = this.authService.waitLoginSpinner;

  get emailIsInvalid() {
    const { errors } = this.form.controls["email"];
    return errors?.["email"] || errors?.["pattern"];
  }

  constructor() {
    super();

    effect(() => {
      const user = this.authService.user();
      const error = this.error();
      this.user.set(user);


      if (error && 'ok' in error && !error.ok) {
        this.setInfo({ ...this.error() });
      }

      if (user && 'email' in user && user.email) {
        this.close();
      }
    });
  }

  public ngOnInit(): void {
    this.form = new FormGroup({
      password: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      email: new FormControl("", [
        Validators.pattern(this.emailRegExp),
        Validators.required,
        Validators.email,
      ])
    });
  }

  protected signIn() {
    const { email, password } = this.form.getRawValue();
    this.removeInfo();
    this.authService.login(email, password);
  }

  protected async goRegister() {
    this.removeInfo();
    this.authService.user.set(null);
    this.dialogsService.showRegisterDialog();
    if (this.dialogRef) this.dialogRef.close('switch');
  }
}
