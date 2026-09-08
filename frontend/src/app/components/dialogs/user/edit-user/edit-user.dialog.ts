import { Component, inject, OnInit, WritableSignal } from '@angular/core';
import { MatDialogActions } from "@angular/material/dialog";
import { MatFormField, MatInputModule, MatLabel } from "@angular/material/input";
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { AUTH } from '../../../../app.constants';
import { MatIcon } from '@angular/material/icon';
import { NoWhitespaceDirective } from '../../../../directives/no-whitespace.directive';
import { ApplicationDialogClass } from '../../app.dialog.class';
import { MarkControlOnBlurDirective } from '../../../../directives/mark-control-on-blur.directive';
import { UsersService } from '../../../../services/users.service';

@Component({
  selector: 'app-add-user-dialog',
  imports: [MatDialogActions, MatLabel, MatFormField, MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatProgressSpinner, MatIcon, NoWhitespaceDirective, MarkControlOnBlurDirective],
  templateUrl: `edit-user.dialog.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export default class EditUserDialogComponent extends ApplicationDialogClass implements OnInit {
  protected component: typeof EditUserDialogComponent = EditUserDialogComponent;

  protected usersService: UsersService = inject(UsersService);

  protected readonly loading: WritableSignal<boolean> = this.authService.waitUserAddSpinner;

  constructor() {
    super();
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
    const { username, email, password } = this.form.getRawValue();

    this.usersService.update({ id, username, email, password })
      .subscribe({
        next: res => {
          console.log('[UPDATE User]:', res);

          this.removeInfo();
          this.close({ ok: true });
        },

        error: error => {
          console.error(error)

          const message = error.error?.message || 'Failed to update User';
          this.setInfo({ ok: false, message });
        }
      });
  }

}
