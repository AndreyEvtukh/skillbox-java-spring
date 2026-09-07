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

@Component({
  selector: 'app-add-user-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MatIcon, NoWhitespaceDirective],
  templateUrl: `add-user-dialog.html`,
  styleUrl: 'add-user-dialog.css',
  animations: [AUTH.STATUS_ANIMATION],
})
export default class AddUserDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof AddUserDialogComponent = AddUserDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<AddUserDialogComponent>, { optional: true });

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  get emailIsInvalid() {
    const { errors } = this.form.controls["email"];
    return errors?.["email"] || errors?.["pattern"];
  }

  constructor() {
    super();

    effect(() => {
      const user = this.authService.user();
      this.user.set(user);

      if (this.error()) {
        this.setInfo({ ok: false, message: this.error() });
      }
    });
  }

  public ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
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

  protected add() {
    const { username, email, password } = this.form.getRawValue();

    try {
      this.authService.addUser(username, email, password);
      this.removeInfo();
      this.close();
    } catch (err) {
      console.error(err)
    }

  }
}
