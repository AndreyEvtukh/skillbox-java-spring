import { Component,  } from '@angular/core';
import ForRegisteredOnlyController from "../../components/for-registered-only/forRegisteredOnly";
import { ApplicationPageClass } from '../app.pages.class';

@Component({
    imports: [
        ForRegisteredOnlyController
    ],
  selector: 'app-booking-page',
  templateUrl: './booking.page.html',
})
export default class BookingPageController extends ApplicationPageClass {

}

