import { Component, inject } from '@angular/core';
import { ApplicationPageClass } from '../app.pages.class';
import ForRegisteredOnlyController from '../../components/for-registered-only/forRegisteredOnly';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridOptions } from 'ag-grid-community';
import { User, UsersService } from '../../services/users.service';

@Component({
  imports: [
    ForRegisteredOnlyController,
    AgGridAngular
  ],
  selector: 'app-users-page',
  templateUrl: './users.page.html',
})
export default class UsersPageController extends ApplicationPageClass {
  protected readonly usersService: UsersService = inject(UsersService);

  protected readonly columnDefs: ColDef[] = [
    { field: 'email', headerName: 'Email', type: 'emailCol', sort: 'asc' },
    { field: 'username', headerName: 'User Name', type: 'usernameCol' },
    { field: 'role', headerName: 'User Role', type: 'roleCol' },
    { field: 'active', headerName: 'Status', type: 'statusCol' },
    { field: 'actions', headerName: 'Actions', type: 'actionsCol' }
  ];

  protected readonly columnTypes = {
    emailCol: { flex: 0.8, minWidth: 200, },
    usernameCol: { flex: 0.8, minWidth: 300, },
    roleCol: { flex: 0.4, minWidth: 100, },
    statusCol: {
      sortable: true, filter: true, flex: 0.6, minWidth: 120,
      cellRenderer: (params: any) => `<span class="${params.value ? 'text-green-600' : 'text-red-500'}">${params.value ? 'Active' : 'Inactive'}</span>`
    },
    actionsCol: {
      sortable: false, filter: false, maxWidth: 130,
      cellRenderer: (params: any) => this.buildActionsCell(params.data.role === "ADMIN")
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

  protected updateContent() {
    this.usersService.getAll().subscribe({
      next: (data) => this.rowData.set(data),
      error: error => console.log(error)
    });
  }

  protected override onAdd() {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showAddUserDialog()
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) this.updateContent();
      });
  }

  protected override async onEdit(user: User) {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showEditUserDialog({ user })
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) this.updateContent();
      });
  }

  protected override onDelete(user: User) {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    const config = {
      html: `Are you sure you want to delete <span class="font-medium">${user.username}</span>?`,
      confirmButtonText: 'Delete',
      onConfirm: () => this.usersService.delete(user)
    }

    return this.dialogsService.showConfirmationDialog(config)
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          console.log(`[DELETE User]: ${user.id}`);
          this.updateContent();
        }
      });
  }
}

