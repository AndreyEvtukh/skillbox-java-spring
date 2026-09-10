import { inject, Injectable, signal, WritableSignal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, of, catchError } from 'rxjs';
import { User } from './users.service';
import { ErrorMessageService } from './error-message.service';

export interface LoginResponse {
  username: string;
  email: string;
  role: string;
  active: boolean;
}

export interface LogoutResponse {
  ok: boolean;
  email: string;
  active: boolean;
}

export type CheckActiveResponse = {
  email?: string;
  active?: boolean;
  ok?: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly errorMessageService: ErrorMessageService = inject(ErrorMessageService);

  private readonly url = `http://localhost:8082/api/v1/`;
  private readonly storageKey = 'auth_user';

  public readonly waitLoginSpinner: WritableSignal<boolean> = signal(false);
  public readonly waitUserAddSpinner: WritableSignal<boolean> = signal(false);

  public readonly user: WritableSignal<User | null> = signal<User | null>(null);
  public readonly error = this.errorMessageService.loginError;

  constructor() {
    this.waitLoginSpinner.set(true);
    this.loadUser().subscribe(user => this.user!.set(user as User));
  }

  register(email: string, username: string, password: string) {
    this.waitLoginSpinner.set(true);
    this.error.set(null);

    this.http
      .post<LoginResponse>(this.url + "user", { email, username, password })
      .subscribe({
          next: () => this.login(email, password),
          error: error => this.onLoginFail(error),
        }
      );
  }

  login(email: string, password: string) {
    this.waitLoginSpinner.set(true);
    this.error.set(null);

    const credentials = btoa(`${email}:${password}`);

    this.http
      .post<LoginResponse>(this.url + 'login', { email, password })
      .subscribe({
        next: (res: LoginResponse) => this.onLoginSuccess(res, credentials),
        error: error => this.onLoginFail(error),
      });
  }

  logout(): void {
    const email = this.user()?.email;
    if (!email) return;

    this.waitLoginSpinner.set(true);
    this.http.post<LogoutResponse>(this.url + 'logout', { email })
      .subscribe({
        next: (res: LogoutResponse) => {
          if (res.ok) {
            this.user.set(null);
            this.waitLoginSpinner.set(false);
            this.error.set(null);

            localStorage.removeItem(this.storageKey);
            sessionStorage.removeItem('auth_credentials');
          }
        },

        error: error => {
          const message = error.error?.message || error.message || 'Logout failed';
          this.waitLoginSpinner.set(false);
          this.error.set(message);
        }
      });
  }

  private loadUser(): Observable<LoginResponse | null> {
    const credentials = sessionStorage.getItem('auth_credentials');

    if (!credentials) {
      this.waitLoginSpinner.set(false);
      return of(null);
    }

    const decoded = atob(credentials);
    const separatorIndex = decoded.indexOf(':');

    if (separatorIndex === -1) {
      sessionStorage.removeItem('auth_credentials');
      this.waitLoginSpinner.set(false);
      return of(null);
    }

    const email = decoded.substring(0, separatorIndex);

    return this.http
      .post<CheckActiveResponse>(
        this.url + 'me',
        { email },
        {
          headers: {
            Authorization: `Basic ${credentials}`
          }
        }
      )
      .pipe(
        map(res => {
          if (!res.ok) {
            sessionStorage.removeItem('auth_credentials');
            localStorage.removeItem(this.storageKey);
            return null;
          }

          const storedUser = localStorage.getItem(this.storageKey);
          if (!storedUser) {
            this.user.set(null);
            this.waitLoginSpinner.set(false);
            this.error.set(null);
            return null;
          }

          const user = JSON.parse(storedUser) as LoginResponse;
          const updatedUser: LoginResponse = { ...user, active: res.active ?? false };

          localStorage.setItem(this.storageKey, JSON.stringify(updatedUser));
          this.waitLoginSpinner.set(false);

          console.log("[LOAD User]: ", updatedUser)

          return updatedUser;
        }),

        catchError(() => {
          sessionStorage.removeItem('auth_credentials');
          localStorage.removeItem(this.storageKey);
          this.waitLoginSpinner.set(false);
          return of(null);
        })
      );
  }

  private onLoginSuccess(res: LoginResponse, credentials: string) {
    this.user!.set(res);
    this.waitLoginSpinner.set(false);
    console.log("[SUCCESS][Login]: ", res)
    localStorage.setItem(this.storageKey, JSON.stringify(res));
    sessionStorage.setItem('auth_credentials', credentials);
  }

  private onLoginFail(error: any) {
    let message = error.error?.message || error.message;
    if (error.status === 0) {
      message = "Unable to connect to the server";
    }
    this.user!.set(null);
    this.waitLoginSpinner.set(false);
    this.error.set(message);
  }
}
