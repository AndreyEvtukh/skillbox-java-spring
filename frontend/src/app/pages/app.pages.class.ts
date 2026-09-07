import { computed, Directive, inject, OnDestroy, Signal, WritableSignal } from '@angular/core';
import { AuthService, User } from '../services/auth-service';
import { HttpClient } from '@angular/common/http';

@Directive()
export abstract class ApplicationPageClass implements OnDestroy {
  protected readonly authService = inject(AuthService);
  protected readonly http = inject(HttpClient);

  protected readonly url = `http://localhost:8082/api/v1`;

  protected user: WritableSignal<User | null> = this.authService.user;
  protected isAdmin: Signal<boolean> = computed(() => this.user()?.role === 'ADMIN');

  ngOnDestroy() {

  }
}
