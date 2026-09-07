import { Component, effect, inject, signal, WritableSignal, } from '@angular/core';
import { ApplicationPageClass } from '../app.pages.class';
import ForRegisteredOnlyController from '../../components/for-registered-only/forRegisteredOnly';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridOptions } from 'ag-grid-community';
import { User } from '../../services/auth-service';
import { DialogsService } from '../../services/dialogs.service';

@Component({
  imports: [
    ForRegisteredOnlyController,
    AgGridAngular
  ],
  selector: 'app-users-page',
  styleUrl: './users.page.css',
  templateUrl: './users.page.html',
})
export default class UsersPageController extends ApplicationPageClass {
  protected readonly rowData: WritableSignal<any> = signal([]);
  protected dialogsService: DialogsService = inject(DialogsService);


  protected readonly columnDefs: ColDef[] = [
    { field: 'id', headerName: 'User ID', type: 'idCol' },
    { field: 'email', headerName: 'Email', type: 'emailCol',  sort: 'asc' },
    { field: 'username', headerName: 'User Name', type: 'usernameCol' },
    { field: 'role', headerName: 'User Role', type: 'roleCol' },
    { field: 'active', headerName: 'Status', type: 'statusCol' },
    { field: 'actions', headerName: 'Actions', type: 'actionsCol' }
  ];

  protected readonly columnTypes = {
    idCol: { flex: 1.2, minWidth: 300, },
    emailCol: { flex: 0.8, minWidth: 200, },
    usernameCol: { flex: 0.8, minWidth: 300, },
    roleCol: { flex: 0.4, minWidth: 100, },
    statusCol: {
      sortable: true, filter: true, flex: 0.6, minWidth: 120,
      cellRenderer: (params: any) => `<span class="${params.value ? 'text-green-600' : 'text-red-500'}">${params.value ? 'Active' : 'Inactive'}</span>`
    },
    actionsCol: {
      sortable: false, filter: false, flex: 0.6, minWidth: 120,
      cellRenderer: (params: any) => this.buildActionsCell(params.data)
    }
  };

  protected readonly gridOptions: GridOptions = {
    columnDefs: this.columnDefs,
    columnTypes: this.columnTypes,
    suppressCellFocus: true,

    defaultColDef: {
      flex: 1,
      sortable: true,
      filter: true,
      resizable: true
    },

    pagination: true,
    paginationPageSize: 10,
    paginationPageSizeSelector: [10, 20, 50, 100],
    animateRows: true,

    onCellClicked: this.onCellClicked.bind(this)
  };

  constructor() {
    super();
    effect(() => {
      const user = this.user();

      if (user === null) {
        this.rowData.set(null);
      } else {
        this.getUsers();
      }
    });
  }

  private getUsers() {
    try {
      this.http.get(`${this.url}/user/all`, { params: { _: Date.now() } }).subscribe(
        {
          next: (data: any) => {
            this.rowData.set(data);
          },
          error: error => {
            console.log(error)
          }
        }
      )
    } catch (error) {
      console.log(error);
    }
  }

  addUser() {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    return this.dialogsService.showAddUserDialog()
      .afterClosed()
      .subscribe(result => {
        if (result) {
          this.getUsers();
        }
      });
  }

  onCellClicked(event: any): void {
    if (event.colDef.field !== 'actions') {
      return;
    }

    const target = event.event?.target as HTMLElement;
    if (target.closest('.edit-user')) this.editUser(event.data);
    if (target.closest('.remove-user')) this.deleteUser(event.data);
  }

  private buildActionsCell(user: User): string {
    const editBtn = document.createElement('div');
    editBtn.className = 'edit-user leading-relaxed cursor-pointer bg-green-600 hover:bg-green-700 !font-monospace text-dark-9 !rounded-sm !px-2 !py-1 !text-12 transition-all duration-200';
    editBtn.innerHTML = 'Edit';

    const deleteBtn = document.createElement('div');
    deleteBtn.className = 'remove-user leading-relaxed cursor-pointer bg-red-500 hover:bg-red-600 !font-monospace text-dark-9 !rounded-sm !px-2 !py-1 !text-12 transition-all duration-200';
    deleteBtn.innerHTML = 'Delete';

    if (user.role === "ADMIN") {
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

  editUser(user: User) {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    return this.dialogsService.showEditUserDialog({ user })
      .afterClosed()
      .subscribe(result => {
        if (result) {
          this.getUsers();
        }
      });
  }

  deleteUser(user: User) {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    const config = {
      message: "Are you sure you want to delete this user?",
      onConfirm: () => this.authService.deleteUser(user.id!)
    }

    return this.dialogsService.showConfirmationDialog(config)
      .afterClosed()
      .subscribe(result => {
        if (result) {
          this.getUsers();
        }
      });
  }
}

