import { Component } from '@angular/core';
import ForRegisteredOnlyController from "../../components/for-registered-only/forRegisteredOnly";
import { ApplicationPageClass } from '../app.pages.class';

@Component({
    imports: [
        ForRegisteredOnlyController
    ],
  selector: 'app-rooms-page',
  templateUrl: './rooms.page.html',
})
export default class RoomsPageController extends ApplicationPageClass {
}

