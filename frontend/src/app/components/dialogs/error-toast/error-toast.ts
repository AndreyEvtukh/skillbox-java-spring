import { Component, inject, OnDestroy, WritableSignal } from '@angular/core';
import { ErrorMessageService } from '../../../services/error-message.service';
import { AUTH } from '../../../app.constants';

@Component({
  selector: 'app-error-toast',
  imports: [],
  templateUrl: `error-toast.html`,
  animations: [AUTH.STATUS_ANIMATION],
})
export class ErrorToastController implements OnDestroy {
  private readonly errorMessageService: ErrorMessageService = inject(ErrorMessageService)
  protected readonly error: WritableSignal<any> = this.errorMessageService.loginError;

  public ngOnDestroy() {
    this.error.set(null);
  }
}
