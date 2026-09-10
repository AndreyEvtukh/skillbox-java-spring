import { Injectable, signal, WritableSignal } from '@angular/core';

@Injectable({
  providedIn: "root",
})
export class ErrorMessageService {
  public readonly loginError: WritableSignal<{ ok: boolean, message: string } | null> = signal<{
    ok: boolean,
    message: string
  } | null>(null);
}
