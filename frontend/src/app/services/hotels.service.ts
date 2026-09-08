import { inject, Injectable } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

  // === GET ALL HOTELS === //
  public getAll(): Observable<Array<Hotel>> {
    return this.http.get<Array<Hotel>>(this.url + "/all")
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
    });
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
    });
  }

  // === DELETE === //
  public delete(request: Hotel): Observable<void> {
    const { id } = request;
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
