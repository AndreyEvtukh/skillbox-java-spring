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
    templateUrl: `info.html`,
    styleUrl: 'info.css',
})
export class InfoDialogController extends ApplicationDialogClass {
    protected component: typeof InfoDialogController = InfoDialogController;
    protected override dialogRef: MatDialogRef<any> | null = inject(MatDialogRef<InfoDialogController>, { optional: true });
}
