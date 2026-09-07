import { AbstractControl, NG_VALIDATORS, ValidationErrors, Validator } from '@angular/forms';
import { Directive, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Directive({
    selector: '[matchPassword]',
    standalone: true,
    providers: [
        {
            provide: NG_VALIDATORS,
            useExisting: MatchPasswordDirective,
            multi: true
        }
    ]
})
export class MatchPasswordDirective implements Validator, OnDestroy {
    @Input('matchPassword') matchTo!: string;

    private subscription?: Subscription;

    validate(control: AbstractControl): ValidationErrors | null {
        if (!control.parent || !this.matchTo) return null;

        const matchingControl = control.parent.get(this.matchTo);
        if (!matchingControl) return null;


        if (!this.subscription) {
            this.subscription = matchingControl.valueChanges.subscribe(() => {
                control.updateValueAndValidity({ onlySelf: true, emitEvent: false });
            });
        }

        matchingControl.updateValueAndValidity({ onlySelf: true, emitEvent: false });

        if (!control.value || !matchingControl.value) return null;

        return control.value === matchingControl.value
            ? null
            : { matchPassword: true };
    }

    ngOnDestroy(): void {
        this.subscription?.unsubscribe();
    }
}
