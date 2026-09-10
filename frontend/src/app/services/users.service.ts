import { effect, inject, Injectable, signal, WritableSignal } from "@angular/core";
import { MatDialog } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { AuthService } from './auth.service';

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
  private readonly authService = inject(AuthService);
  private readonly url = `http://localhost:8082/api/v1/user`;

  public readonly allUsers: WritableSignal<User[]> = signal<User[]>([]);
  public readonly user = this.authService.user;

  constructor() {
    effect(() => {
      if (!this.user()) {
        this.allUsers.set([]);
      }
    });
  }

  // === GET ALL USERS === //
  public getAll(): Observable<Array<User>> {
    if (!this.user()) return of();

    if (this.allUsers().length) {
      return of(this.allUsers());
    }
    return this.http.get<User[]>(`${this.url}/all`).pipe(
      tap(hotels => {
        this.allUsers.set(hotels);
      })
    );
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
    if (!this.user()) return of();

    const { username, email, password } = request;
    return this.http.post<User>(this.url, {
      username,
      email,
      password
    }).pipe(
      tap(user => this.allUsers.update(users => [...users, user]))
    );
  }

  // === UPDATE === //
  public update(request: User): Observable<User> {
    if (!this.user()) return of();

    const { id, username, email, password } = request;
    return this.http.put<User>(this.url + '/' + id, {
      username, email, password
    }).pipe(
      tap(updatedHotel =>
        this.allUsers.update(users => users.map(user => user.id === updatedHotel.id ? { ...user, ...updatedHotel } : user))
      )
    );
  }

  // === DELETE === //
  public delete(request: User): Observable<void> {
    if (!this.user()) return of();

    const { id } = request;
    return this.http
      .delete<void>(`${this.url}/${id}`)
      .pipe(
        tap(() => this.allUsers.update(users => users.filter(user => user.id !== id)))
      );
  }
}
