import { computed, Directive, effect, inject, OnDestroy, signal, Signal, WritableSignal } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../services/users.service';
import { DialogsService } from '../services/dialogs.service';

@Directive()
export abstract class ApplicationPageClass implements OnDestroy {
  protected readonly rowData: WritableSignal<any> = signal([]);
  protected readonly dialogsService: DialogsService = inject(DialogsService);

  protected readonly authService = inject(AuthService);
  protected readonly http = inject(HttpClient);

  protected readonly url = `http://localhost:8082/api/v1`;

  protected user: WritableSignal<User | null> = this.authService.user;
  protected isAdmin: Signal<boolean> = computed(() => this.user()?.role === 'ADMIN');

  constructor() {
    effect(() => {
      const user = this.user();

      if (user === null) {
        this.rowData.set(null);
      } else {
        this.updateContent();
      }
    });
  }

  protected buildActionsCell(isAdmin: Boolean): string {
    const editBtn = document.createElement('div');
    editBtn.className = 'edit-btn leading-relaxed cursor-pointer bg-green-600 hover:bg-green-700 !font-monospace text-dark-9 !rounded-sm !px-2 !py-1 !text-12 transition-all duration-200';
    editBtn.innerHTML = 'Edit';

    const deleteBtn = document.createElement('div');
    deleteBtn.className = 'remove-btn leading-relaxed cursor-pointer bg-red-500 hover:bg-red-600 !font-monospace text-dark-9 !rounded-sm !px-2 !py-1 !text-12 transition-all duration-200';
    deleteBtn.innerHTML = 'Delete';

    if (isAdmin) {
      deleteBtn.classList.add('invisible');
    }

    const container = document.createElement('div');
    container.className = 'flex flex-row w-full h-full items-center justify-center gap-2';

    container.append(editBtn);
    container.append(deleteBtn);

    const result = document.createElement('div');
    result.append(container)

    return result.innerHTML;
  }

  protected onCellClicked(event: any): void {
    if (event.colDef.field !== 'actions') {
      return;
    }

    const target = event.event?.target as HTMLElement;
    if (target.closest('.edit-btn')) this.onEdit(event.data);
    if (target.closest('.remove-btn')) this.onDelete(event.data);
  }

  protected abstract onAdd(event?: any): unknown;

  protected abstract onEdit(event?: any): unknown;

  protected abstract onDelete(event?: any): unknown;

  protected getPermissions(): boolean {
    if (!this.isAdmin()) {
      this.dialogsService.showNoPermissionsDialog();
      return false;
    }
    return true;
  }

  protected abstract updateContent(): void;

  ngOnDestroy() {

  }
}
