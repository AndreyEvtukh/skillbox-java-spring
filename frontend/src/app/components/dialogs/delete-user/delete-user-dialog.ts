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
import { MarkControlOnBlurDirective } from '../../../directives/mark-control-on-blur.directive';

@Component({
  selector: 'app-add-user-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MatIcon, NoWhitespaceDirective, MarkControlOnBlurDirective],
  templateUrl: `delete-user-dialog.html`,
  styleUrl: 'delete-user-dialog.css',
  animations: [AUTH.STATUS_ANIMATION],
})
export default class DeleteUserDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof DeleteUserDialogComponent = DeleteUserDialogComponent;
  protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<any>, { optional: true });

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  get emailIsInvalid() {
    const {errors} = this.form.controls["email"];
    return errors?.["email"] || errors?.["pattern"];
  }

  constructor() {
    super();

    effect(() => {
      const user = this.authService.user();
      this.user.set(user);

      if (this.error()) {
        this.setInfo({ok: false, message: this.error()});
      }
    });
  }

  public ngOnInit(): void {
    this.form = new FormGroup({
      username: new FormControl(this.data.username, [
        Validators.required,
        Validators.minLength(1),
      ]),
      password: new FormControl("", [
        Validators.required,
        Validators.minLength(1),
      ]),
      email: new FormControl(this.data.email, [
        Validators.pattern(this.emailRegExp),
        Validators.required,
        Validators.email,
      ])
    });
  }

  protected async save() {
    const id = this.data.id;
    const {username, email, password} = this.form.getRawValue();

    try {
      this.authService.editUser(id, username, email, password);
      this.removeInfo();
      this.close();
    } catch (err) {
      console.error(err)
    }
  }

}
