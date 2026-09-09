import { inject, Injectable, signal, WritableSignal } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of, tap } from 'rxjs';

export type Booking = {
  id?: string;
  userId: string;
  roomId: string;
  checkIn: string;
  checkOut: string;
};

@Injectable({
  providedIn: "root",
})
export class BookingService {
  protected readonly dialog = inject(MatDialog);
  private readonly http = inject(HttpClient);
  private readonly url = `http://localhost:8082/api/v1/booking`;

  public readonly allBookings: WritableSignal<Booking[]> = signal<Booking[]>([]);

  // === GET ALL HOTELS === //
  public getAll(): Observable<Array<Booking>> {
    if (this.allBookings().length) {
      return of(this.allBookings());
    }
    return this.http.get<Booking[]>(`${this.url}/all`).pipe(
      tap(bookings => {
        this.allBookings.set(bookings);
      })
    );
  }

  // === CREATE === //
  public add(request: Booking): Observable<{ok: boolean}> {
    const { roomId, userId, checkOut, checkIn } = request;
    return this.http.post<Booking>(this.url, {
      roomId,
      userId,
      checkIn,
      checkOut
    }).pipe(
      tap(booking => {
        this.allBookings.update(bookings => [...bookings, booking]);
      }),
      map(() => {
        return {ok: true};
      })
    );
  }
}
