import { Component, inject } from '@angular/core';
import { MatDialogActions, MatDialogRef } from "@angular/material/dialog";
import { MatIcon } from "@angular/material/icon";
import { ApplicationDialogClass } from '../app.dialog.class';

@Component({
    selector: 'app-confirmation-dialog',
    imports: [
        MatDialogActions,
        MatIcon
    ],
    templateUrl: `confirmation.html`,
    styleUrl: 'confirmation.css',
})
export class ConfirmationDialogController extends ApplicationDialogClass {
    protected component: typeof ConfirmationDialogController = ConfirmationDialogController;
    protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<ConfirmationDialogController>, { optional: true });
}
