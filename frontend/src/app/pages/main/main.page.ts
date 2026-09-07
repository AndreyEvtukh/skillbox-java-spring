import { Component, signal } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-main-page',
  styleUrl: './main.page.css',
  templateUrl: './main.page.html',
})
export default class MainPageController {

  public mainFeatures = signal([
    {
      index: 0,
      title: 'Hotel Search',
      text: 'Search for hotels and filter them by rating and other criteria.',
    }, {
      index: 1,
      title: 'Room Booking',
      text: 'Check room availability, select dates, and book a suitable room.',
    }, {
      index: 2,
      title: 'User Ratings',
      text: 'Users can rate hotels and share their experience with ratings from 1 to 5.',
    }, {
      index: 3,
      title: 'Booking Management',
      text: 'Manage existing reservations and keep track of booking periods.',
    }, {
      index: 4,
      title: 'Administrative CMS',
      text: 'Manage hotels, rooms, and application content through the administrative interface.',
    }, {
      index: 5,
      title: 'Statistics',
      text: 'Monitor service statistics and export statistical data in CSV format.'
    },
  ]);

  public BE_Tech = signal([
    "Java 21",
    "Spring Boot 4.1.1",
    "Spring Web MVC",
    "Spring Data JPA",
    "PostgreSQL 18",
    "Flyway",
    "MapStruct",
    "Spring Security",
    "SpringDoc OpenAPI",
    "Gradle Kotlin DSL",
    "Docker",
    "Docker Compose",
    "JavaDoc"
  ]);

  public FE_Tech = signal([
    "Angular 22",
    "TypeScript 6",
    "AG Grid",
    "RxJS",
    "Tailwind CSS 4",
    "Vitest",
    "PostCSS",
    "Flex/Grid",
  ]);


}

