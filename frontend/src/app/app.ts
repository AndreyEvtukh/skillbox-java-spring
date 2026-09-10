import { Component, inject, OnInit } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import appHeaderController from './components/app-header/app.header';
import appFooterController from './components/app-footer/app.footer';
import { IconService } from './services/icon.service';

@Component({
  imports: [RouterOutlet, appHeaderController, appFooterController],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App implements OnInit {
  protected iconService: IconService = inject(IconService);

  ngOnInit() {
    this.iconService.registerImageIcons();
  }
}
