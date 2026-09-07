import { Component, signal } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-footer',
  styleUrl: './app.footer.css',
  templateUrl: './app.footer.html',
})
export default class appFooterController {
  public date = signal(new Date().getFullYear());

}

