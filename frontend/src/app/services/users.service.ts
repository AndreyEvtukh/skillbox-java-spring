import { inject, Injectable } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type User = {
  id?: string;
  username?: string;
  email?: string;
  role?: string;
  active?: boolean;
  ok?: boolean;
  message?: string;
  password?: string;
  event?: "edit" | 'add' | 'delete';
};

@Injectable({
  providedIn: "root",
})
export class UsersService {
  protected readonly dialog = inject(MatDialog);
  private readonly http = inject(HttpClient);
  private readonly url = `http://localhost:8082/api/v1/user`;

  // === GET ALL USERS === //
  public getAll():Observable<Array<User>> {
    return this.http.get<Array<User>>(this.url + "/all");
  }

  // === GET === //
  public get(request: User): Observable<User> {
    const username: string = request.username as string;
    return this.http.get<User>(this.url, {
      params: { username }
    });
  }

  // === CREATE === //
  public add(request: User): Observable<User> {
    const { username, email, password } = request;
    return this.http.post<User>(this.url, { username, email, password });
  }

  // === UPDATE === //
  public update(request: User): Observable<User> {
    const { id, username, email, password } = request;
    return this.http.put<User>(this.url + '/' + id, { username, email, password });
  }

  // === DELETE === //
  public delete(request: User): Observable<void> {
    const { id } = request;
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
