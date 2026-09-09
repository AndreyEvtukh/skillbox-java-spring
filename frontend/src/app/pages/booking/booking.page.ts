import { Component, inject, WritableSignal, } from '@angular/core';
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
  protected bookingService: BookingService = inject(BookingService);
  protected hotelsService: HotelsService = inject(HotelsService);
  protected roomsService: RoomsService = inject(RoomsService);
  protected usersService: UsersService = inject(UsersService);

  protected datePipe: FormatDatePipe = inject(FormatDatePipe);

  protected hotels: WritableSignal<Hotel[]> = this.hotelsService.allHotels;
  protected rooms: WritableSignal<Room[]> = this.roomsService.allRooms;
  protected users: WritableSignal<User[]> = this.usersService.allUsers;

  protected readonly columnDefs: ColDef[] = [
    { field: 'userName', headerName: 'User Name', type: 'nameCol', sort: 'asc' },
    { field: 'email', headerName: 'User Email', type: 'emailCol' },
    { field: 'category', headerName: 'Room Category', type: 'categoryCol' },
    { field: 'number', headerName: 'Room Number', type: 'numberCol' },
    { field: 'hotel', headerName: 'Hotel', type: 'hotelCol' },
    { field: 'city', headerName: 'City', type: 'cityCol' },
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

    this.bookingService.getAll().subscribe({
      next: (data: Array<Booking>) => {
        const rowData = data.map((item: any) => {
          const user: User = this.users().find(user => user.id === item.userId)!;
          item.email = user.email;
          item.userName = user.username;

          const room: Room = this.rooms().find(room => room.id === item.roomId)!;
          item.category = room.name;
          item.number = room.number;

          const hotel: Hotel = this.hotels().find(hotel => hotel.id === room!.hotelId)!;
          item.hotel = hotel.name;
          item.city = hotel.city;

          return item;
        });

        this.rowData.set(rowData)
      },
      error: error => console.log(error)
    })
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

