import { Component, inject, signal, WritableSignal, } from '@angular/core';
import ForRegisteredOnlyController from "../../components/for-registered-only/forRegisteredOnly";
import { ApplicationPageClass } from '../app.pages.class';
import { Hotel, HotelsService } from '../../services/hotels.service';
import { Booking, BookingService } from '../../services/bokking.service';
import { ColDef, GridOptions, ValueFormatterParams } from 'ag-grid-community';
import { AgGridAngular } from 'ag-grid-angular';
import { Room, RoomsService } from '../../services/rooms.service';
import { User, UsersService } from '../../services/users.service';
import { firstValueFrom } from 'rxjs';
import { FormatDatePipe } from '../../pipes/formatDatePipe';

@Component({
  imports: [
    ForRegisteredOnlyController,
    AgGridAngular
  ],
  providers: [
    FormatDatePipe
  ],
  selector: 'app-booking-page',
  templateUrl: './booking.page.html',
})
export default class BookingPageController extends ApplicationPageClass {
  protected readonly bookingService: BookingService = inject(BookingService);
  protected readonly hotelsService: HotelsService = inject(HotelsService);
  protected readonly roomsService: RoomsService = inject(RoomsService);
  protected readonly usersService: UsersService = inject(UsersService);

  protected readonly datePipe: FormatDatePipe = inject(FormatDatePipe);
  protected readonly loadData = signal(false);

  protected readonly hotels: WritableSignal<Hotel[]> = this.hotelsService.allHotels;
  protected readonly rooms: WritableSignal<Room[]> = this.roomsService.allRooms;
  protected readonly users: WritableSignal<User[]> = this.usersService.allUsers;

  protected readonly columnDefs: ColDef[] = [
    { field: 'userName', headerName: 'User Name', type: 'nameCol', sort: 'asc' },
    { field: 'email', headerName: 'User Email' },
    { field: 'category', headerName: 'Room Category' },
    { field: 'number', headerName: 'Room Number' },
    { field: 'hotel', headerName: 'Hotel' },
    { field: 'city', headerName: 'City' },
    { field: 'checkIn', headerName: 'Check In', type: 'dateCol' },
    { field: 'checkOut', headerName: 'Check Out', type: 'dateCol' }
  ];

  protected readonly columnTypes = {
    nameCol: { flex: 0.4, minWidth: 130, filter: false },
    dateCol: {
      flex: 0.4,
      minWidth: 120,
      filter: false,
      valueFormatter: (params: ValueFormatterParams) => this.datePipe.transform(params.value)
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

      tooltipValueGetter: params => params.value != null ? String(params.value) : ''
    },
  }

  protected async updateContent() {
    if (this.loadData() || this.rowData()) return;

    this.loadData.set(true);

    try {
      const q = [];
      if (!this.users().length) {
        q.push(firstValueFrom(this.usersService.getAll()));
      }

      if (!this.rooms().length) {
        q.push(firstValueFrom(this.roomsService.getAll()));
      }

      if (!this.hotels().length) {
        q.push(firstValueFrom(this.hotelsService.getAll()));
      }
      await Promise.all(q);

      if (
        !this.users().length ||
        !this.rooms().length ||
        !this.hotels().length
      ) {
        return;
      }

      const data = await firstValueFrom(this.bookingService.getAll());
      const rowData = data.map((item: Booking & any) => {
        const user = this.users().find(user => user.id === item.userId)!;
        const room = this.rooms().find(room => room.id === item.roomId)!;
        const hotel = this.hotels().find(hotel => hotel.id === room.hotelId)!;

        return {
          ...item,
          email: user.email,
          userName: user.username,
          category: room.name,
          number: room.number,
          hotel: hotel.name,
          city: hotel.city
        };
      });
      this.rowData.set(rowData);
    } catch (error) {
      console.error(error);
    } finally {
      this.loadData.set(false);
    }
  }

  protected override onAdd() {
    const permit: boolean = this.getPermissions();
    if (!permit) return;

    return this.dialogsService.showAddBookingDialog()
      .afterClosed()
      .subscribe(result => {
        if (result?.ok) this.updateContent();
      });
  }

  protected override onEdit(): void {
  };

  protected override onDelete(): void {
  };
}

