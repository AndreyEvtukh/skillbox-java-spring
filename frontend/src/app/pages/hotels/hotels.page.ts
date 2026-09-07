import { Component, } from '@angular/core';
import ForRegisteredOnlyController from '../../components/for-registered-only/forRegisteredOnly';
import { ApplicationPageClass } from '../app.pages.class';

@Component({
  imports: [
    ForRegisteredOnlyController
  ],
  selector: 'app-hotels-page',
  styleUrl: './hotels.page.css',
  templateUrl: './hotels.page.html',
})
export default class HotelsPageController extends ApplicationPageClass{

}

