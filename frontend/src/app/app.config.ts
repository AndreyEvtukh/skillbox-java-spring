import { ApplicationConfig, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter, withInMemoryScrolling, withViewTransitions } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { authInterceptor } from './interceptor/authInterceptor';
import {
  AllCommunityModule,
  ModuleRegistry
} from 'ag-grid-community';

ModuleRegistry.registerModules([
  AllCommunityModule
]);

function initializeApp() {
  return;
}

export const appConfig: ApplicationConfig = {

  providers: [
    provideHttpClient(
      withInterceptors([
        authInterceptor
      ]),
      withFetch()
    ),
    provideBrowserGlobalErrorListeners(),
    provideAppInitializer(() => initializeApp()),
    provideAnimationsAsync(),
    provideRouter(
      routes,
      withViewTransitions(),
      withInMemoryScrolling({
        anchorScrolling: 'enabled',
        scrollPositionRestoration: 'enabled',
      }),
    ),
  ],
};
