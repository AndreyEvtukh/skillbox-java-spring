import { Component, inject, } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogsService } from '../../services/dialogs.service';

@Component({
  imports: [
  ],
  selector: 'app-for-registered-only-page',
  styleUrl: './forRegisteredOnly.css',
  templateUrl: './forRegisteredOnly.html',
})

export default class ForRegisteredOnlyController {
  protected dialog = inject(MatDialog);
  protected readonly dialogsService: DialogsService = inject(DialogsService);

  public goLogin() {
    this.dialogsService.showLoginialog();
  }

  public goRegister() {
    this.dialogsService.showRegisterDialog();
  }
}

