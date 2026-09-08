import { Component, effect, inject, } from '@angular/core';
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
  styleUrl: './hotels.page.css',
  templateUrl: './hotels.page.html',
})
export default class HotelsPageController extends ApplicationPageClass {
  protected hotelsService: HotelsService = inject(HotelsService);

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
      sortable: false, filter: false, flex: 0.6, minWidth: 120,
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

  constructor() {
    super();
    effect(() => {
      const user = this.user();

      if (user === null) {
        this.rowData.set(null);
      } else {
        this.getHotels();
      }
    });
  }

  private getHotels() {
    try {
      this.http.get(`${this.url}/hotel/all`, { params: { _: Date.now() } }).subscribe(
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

  onCellClicked(event: any): void {
    if (event.colDef.field !== 'actions') {
      return;
    }

    const target = event.event?.target as HTMLElement;
    if (target.closest('.edit-btn')) this.editHotel(event.data);
    if (target.closest('.remove-btn')) this.deleteHotel(event.data);
  }

  private buildActionsCell(isAdmin: Boolean): string {
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

  addHotel() {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    return this.dialogsService.showAddHotelDialog()
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          this.getHotels();
        }
      });
  }

  editHotel(hotel: Hotel) {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    return this.dialogsService.showEditHotelDialog({ hotel })
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          this.getHotels();
        }
      });
  }

  deleteHotel(hotel: Hotel) {
    if (!this.isAdmin()) {
      return this.dialogsService.showNoPermissionsDialog();
    }

    const config = {
      html: `Are you sure you want to delete the hotel<p class="font-medium">"${hotel.name}"</p> from the system?`,
      onConfirm: () => this.hotelsService.delete(hotel)
    }

    return this.dialogsService.showConfirmationDialog(config)
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          console.log(`[DELETE Hotel]: ${hotel.id}`);
          this.getHotels();
        }
      });
  }
}

