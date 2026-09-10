import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'main'
  },
  {
    title: "Hotel Booking | Home",
    path: "main",
    loadComponent: () =>
      import('./pages/main/main.page').then(m => m.default)
  },
  {
    title: "Hotel Booking | Hotels",
    path: "hotels",
    loadComponent: () =>
      import('./pages/hotels/hotels.page').then(m => m.default)
  },
  {
    title: "Hotel Booking | Rooms",
    path: "rooms",
    loadComponent: () =>
      import('./pages/rooms/rooms.page').then(m => m.default)
  },
  {
    title: "Hotel Booking | Users",
    path: "users",
    loadComponent: () =>
      import('./pages/users/users.page').then(m => m.default)
  },
  {
    title: "Hotel Booking | Booking",
    path: "booking",
    loadComponent: () =>
      import('./pages/booking/booking.page').then(m => m.default)
  },
  {
    path: '**',
    redirectTo: "main",
  },
];
