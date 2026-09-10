import { Component, inject, } from '@angular/core';
import ForRegisteredOnlyController from '../../components/for-registered-only/forRegisteredOnly';
import { ApplicationPageClass } from '../app.pages.class';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridOptions } from 'ag-grid-community';
import { Hotel, HotelsService } from '../../services/hotels.service';

@Component({
  imports: [
    ForRegisteredOnlyController,
    AgGridAngular
  ],
  selector: 'app-hotels-page',
  templateUrl: './hotels.page.html',
})
export default class HotelsPageController extends ApplicationPageClass {
  protected readonly hotelsService: HotelsService = inject(HotelsService);

  protected readonly columnDefs: ColDef[] = [
    { field: 'name', headerName: 'Name', type: 'nameCol', sort: 'asc' },
    { field: 'title', headerName: 'Title', type: 'titleCol' },
    { field: 'city', headerName: 'City', type: 'cityCol' },
    { field: 'address', headerName: 'Address', type: 'addressCol' },
    { field: 'distance', headerName: 'Distance', type: 'distanceCol' },
    { field: 'rating', headerName: 'Rating', type: 'ratingCol' },
    { field: 'numOfRating', headerName: 'Number Of Ratings', type: 'numOfRatingCol' },
    { field: 'actions', headerName: 'Actions', type: 'actionsCol' }
  ];

  protected readonly columnTypes = {
    nameCol: { flex: 0.4, minWidth: 130, filter: false },
    titleCol: { flex: 0.8, minWidth: 300, filter: false },
    cityCol: { flex: 0.3, minWidth: 100, filter: false },
    addressCol: { flex: 0.4, minWidth: 100, filter: false },
    distanceCol: { flex: 0.4, minWidth: 100, filter: false },
    ratingCol: { flex: 0.4, minWidth: 100, filter: false },
    numOfRatingCol: { flex: 0.4, minWidth: 100, filter: false },
    actionsCol: {
      sortable: false, filter: false, maxWidth: 130,
      cellRenderer: (params: any) => this.buildActionsCell(params.data.role === "ADMIN")
    }
  };

  protected readonly gridOptions: GridOptions = {
    columnDefs: this.columnDefs,
    columnTypes: this.columnTypes,
    suppressCellFocus: true,
    tooltipShowDelay: 0,

    defaultColDef: {
      flex: 1,
      sortable: true,
      filter: true,
      resizable: true,

      tooltipValueGetter: params => {
        return params.value != null ? String(params.value) : '';
      }
    },

    pagination: true,
    paginationPageSize: 10,
    paginationPageSizeSelector: [5, 10, 20],
    animateRows: true,

    onCellClicked: this.onCellClicked.bind(this)
  };

  protected updateContent() {
    this.hotelsService.getAll().subscribe({
      next: (data: any) => this.rowData.set(data),
      error: error => console.log(error)
    })
  }

  protected override onAdd() {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showAddHotelDialog()
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) this.updateContent();
      });
  }

  protected override onEdit(hotel: Hotel) {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showEditHotelDialog({ hotel })
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          this.updateContent();
        }
      });
  }

  protected override onDelete(hotel: Hotel) {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    const config = {
      html: `Are you sure you want to delete the hotel<p class="font-medium">"${hotel.name}"</p> from the system?`,
      onConfirm: () => this.hotelsService.delete(hotel)
    }

    return this.dialogsService.showConfirmationDialog(config)
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          console.log(`[DELETE Hotel]: ${hotel.id}`);
          this.updateContent();
        }
      });
  }
}

