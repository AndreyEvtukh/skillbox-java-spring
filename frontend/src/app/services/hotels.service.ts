import { inject, Injectable, signal, WritableSignal } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';

export type Hotel = {
  id?: string;
  name: string;
  title: string;
  city: string;
  address: string;
  distance: number;
};

@Injectable({
  providedIn: "root",
})
export class HotelsService {
  protected readonly dialog = inject(MatDialog);
  private readonly http = inject(HttpClient);
  private readonly url = `http://localhost:8082/api/v1/hotel`;

  public readonly allHotels: WritableSignal<Hotel[]> = signal<Hotel[]>([]);

  // === GET ALL HOTELS === //
  public getAll(): Observable<Array<Hotel>> {
    if (this.allHotels().length) {
      return of(this.allHotels());
    }
    return this.http.get<Hotel[]>(`${this.url}/all`).pipe(
      tap(hotels => {
        this.allHotels.set(hotels);
      })
    );
  }

  // === GET === //
  public get(request: Hotel): Observable<Hotel> {
    const id: string = request.id as string;
    return this.http.get<Hotel>(this.url + "/" + id);
  }

  // === CREATE === //
  public add(request: Hotel): Observable<Hotel> {
    const { name, title, city, address, distance } = request;
    return this.http.post<Hotel>(this.url, {
      name,
      title,
      city,
      address,
      distance
    }).pipe(
      tap(hotel => {
        this.allHotels.update(hotels => [...hotels, hotel]);
      })
    );
  }

  // === UPDATE === //
  public update(request: Hotel): Observable<Hotel> {
    const { id, name, title, city, address, distance } = request;
    return this.http.put<Hotel>(`${this.url}/${id}`, {
      name: name.trim(),
      title: title.trim(),
      city: city.trim(),
      address: address.trim(),
      distance
    }).pipe(
      tap(updatedHotel => {
        this.allHotels.update(hotels =>
          hotels.map(hotel =>
            hotel.id === updatedHotel.id
              ? { ...hotel, ...updatedHotel }
              : hotel
          )
        );
      })
    );
  }

  // === DELETE === //
  public delete(request: Hotel): Observable<void> {
    const { id } = request;
    return this.http.delete<void>(`${this.url}/${id}`).pipe(
      tap(() => this.allHotels.update(hotels => hotels.filter(hotel => hotel.id !== id)))
    );
  }
}
