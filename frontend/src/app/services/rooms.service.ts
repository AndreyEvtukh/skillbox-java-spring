import { inject, Injectable, signal, WritableSignal } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of, tap } from 'rxjs';

export type Room = {
  id?: string;
  name: string;
  number: number;
  price: number;
  maxCapacity: number;
  description: string;
  closedDates?: string[];
  hotelId?: string;
};

@Injectable({
  providedIn: "root",
})
export class RoomsService {
  protected readonly dialog = inject(MatDialog);
  private readonly http = inject(HttpClient);
  private readonly url = `http://localhost:8082/api/v1/room`;

  public readonly allRooms: WritableSignal<Room[]> = signal<Room[]>([]);

  // === GET ALL ROOMS === //
  public getAll(): Observable<Array<Room>> {
    if (this.allRooms().length) {
      return of(this.allRooms());
    }
    return this.http
      .get<{ page: number; total: number; rooms: Room[] }>(`${this.url}?page=0&size=10&sort=hotelName%2Casc`)
      .pipe(
        tap(data => this.allRooms.set(data.rooms)),
        map(() => this.allRooms())
      );
  }

  // === GET === //
  public get(request: Room): Observable<Room> {
    const id: string = request.id as string;
    return this.http.get<Room>(this.url + "/" + id);
  }

  // === CREATE === //
  public add(request: Room): Observable<Room> {
    const { name, hotelId, description, maxCapacity, price, number } = request;
    return this.http.post<Room>(this.url, {
      name,
      hotelId,
      description,
      maxCapacity,
      price,
      number
    }).pipe(
      tap(room => this.allRooms.update(rooms => [...rooms, room]))
    );
  }

  // === UPDATE === //
  public update(request: Room): Observable<Room> {
    const { id, name, hotelId, description, maxCapacity, price, number } = request;
    return this.http.put<Room>(`${this.url}/${id}`, {
      name: name.trim(),
      description: description.trim(),
      hotelId,
      maxCapacity,
      number,
      price
    }).pipe(
      tap(updatedRoom => {
        this.allRooms.update(rooms =>
          rooms.map(room =>
            room.id === updatedRoom.id
              ? { ...room, ...updatedRoom }
              : room
          )
        );
      })
    );
  }

  // === DELETE === //
  public delete(request: Room): Observable<void> {
    const { id } = request;
    return this.http.delete<void>(`${this.url}/${id}`).pipe(
      tap(() => this.allRooms.update(hotels => hotels.filter(hotel => hotel.id !== id)))
    );
  }
}
