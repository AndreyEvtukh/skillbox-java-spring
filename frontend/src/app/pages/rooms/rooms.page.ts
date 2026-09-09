import { Component, inject } from '@angular/core';
import ForRegisteredOnlyController from "../../components/for-registered-only/forRegisteredOnly";
import { ApplicationPageClass } from '../app.pages.class';
import { Room, RoomsService } from '../../services/rooms.service';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridOptions } from 'ag-grid-community';
import { Hotel, HotelsService } from '../../services/hotels.service';
import { firstValueFrom } from 'rxjs';

@Component({
  imports: [
    ForRegisteredOnlyController,
    AgGridAngular
  ],
  selector: 'app-rooms-page',
  templateUrl: './rooms.page.html',
})
export default class RoomsPageController extends ApplicationPageClass {
  protected roomsService: RoomsService = inject(RoomsService);
  protected hotelsService: HotelsService = inject(HotelsService);

  protected readonly columnDefs: ColDef[] = [
    { field: 'name', headerName: 'Category', type: 'nameCol', sort: 'asc' },
    { field: 'number', headerName: 'Room Number', type: 'numberCol' },
    { field: 'description', headerName: 'Description', type: 'descCol' },
    { field: 'price', headerName: 'Price, $', type: 'priceCol' },
    { field: 'maxCapacity', headerName: 'Max Capacity', type: 'maxCapCol' },
    { field: 'hotel', headerName: 'Hotel', type: 'hotelCol' },
    { field: 'bookingPeriods', headerName: 'Booking Periods', type: 'bookingPeriodsCol' },
    { field: 'actions', headerName: 'Actions', type: 'actionsCol' }
  ];

  protected readonly columnTypes = {
    nameCol: { flex: 0.3, minWidth: 80, filter: false },
    numberCol: { flex: 0.3, minWidth: 80, filter: false },
    descCol: { flex: 1, minWidth: 300, filter: false },
    priceCol: { flex: 0.3, minWidth: 80, filter: false },
    maxCapCol: { flex: 0.3, minWidth: 80, filter: false },
    hotelCol: {
      flex: 0.6, minWidth: 150, filter: false,
      cellRenderer: (params: any) => `${params.data.hotel} (${params.data.city})`,
      tooltipValueGetter: (params: any) => String(`${params.data.hotel} (${params.data.city})`)
    },
    bookingPeriodsCol: { flex: 0.4, minWidth: 100, filter: false },
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

  protected async updateContent() {
    const hotels = await firstValueFrom(this.hotelsService.getAll());
    this.roomsService.getAll().subscribe({
      next: (rooms: any) => {
        const data = rooms.map((item: any) => {
          const { name, city } = hotels.find((hotel: Hotel) => hotel.id === item.hotelId)!;
          item.hotel = name;
          item.city = city;
          return item;
        });
        this.rowData.set(data);
      },
      error: error => console.log(error)
    })
  }

  protected override onAdd() {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showAddRoomDialog()
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) this.updateContent();
      });
  }

  protected override onEdit(room: Room) {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showEditRoomDialog({ room })
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          this.updateContent();
        }
      });
  }

  protected override onDelete(room: Room): unknown {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    const config = {
      html: `Are you sure you want to delete the room?`,
      onConfirm: () => this.roomsService.delete(room)
    }

    return this.dialogsService.showConfirmationDialog(config)
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) {
          console.log(`[DELETE Room]: ${room.id}`);
          this.updateContent();
        }
      });
  }
}

