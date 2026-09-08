import { computed, Directive, inject, OnDestroy, signal, Signal, WritableSignal } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { User } from '../services/users.service';
import { DialogsService } from '../services/dialogs.service';

@Directive()
export abstract class ApplicationPageClass implements OnDestroy {
  protected readonly rowData: WritableSignal<any> = signal([]);
  protected readonly dialogsService: DialogsService = inject(DialogsService);

  protected readonly authService = inject(AuthService);
  protected readonly http = inject(HttpClient);

  protected readonly url = `http://localhost:8082/api/v1`;

  protected user: WritableSignal<User | null> = this.authService.user;
  protected isAdmin: Signal<boolean> = computed(() => this.user()?.role === 'ADMIN');

  ngOnDestroy() {

  }
}
