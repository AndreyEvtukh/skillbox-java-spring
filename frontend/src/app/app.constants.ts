import { animate, style, transition, trigger } from "@angular/animations";

export namespace AUTH {
    export const STATUS_ANIMATION = trigger('spinerAnimation', [
        transition(':enter', [
            style({ opacity: 0, transform: 'scale(0.97)' }),
            animate('200ms ease-out', style({ opacity: 1, transform: 'scale(1)' })),
        ]),
        transition(':leave', [
            animate('200ms ease-in', style({ opacity: 0, transform: 'scale(0.95)' })),
        ]),
    ]);

    export const FADE_ANIMATION = trigger('fadeAnimation', [
        transition(':enter', [
            style({ opacity: 0 }),
            animate('200ms ease-out', style({ opacity: 1 })),
        ]),
        transition(':leave', [
            animate('200ms ease-in', style({ opacity: 0 })),
        ]),
    ]);
}

export const ICONS = {
  Design: "design",
  Frontend: "frontend",
  Backend: "backend",
  Angular: "angular",
  TypeScript: "ts",
  Java: "java",
  ArrowR: "arrow-right",
  Mice: "mice",
  Home: "home",
  About: "about",
  Login: "login",
  USER: "user",
  EYE_OFF: "eye-off",
  EYE_ON: "eye-on",
  DOWNLOAD: "download",
  INFO: "info",
  SUCCESS: "success",
  QUESTION: "quiestion",
  DROPDOWN_ARROW: "dropdown-arrow",
} as const;

export interface ConfirmationDialogConfig {
  title?: string;
  message?: string;
  html?: string;
  confirmButtonText?: string;
  cancelButtonText?: string;
  onConfirm?: () => void;
  onCancel?: () => void;
}

export const STATISTICS_DOC_TYPE = {
  CSV: 'csv',
  PDF: 'pdf'
} as const;



