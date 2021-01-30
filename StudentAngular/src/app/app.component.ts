import { Component } from '@angular/core';
import {WebSocketService} from './shared/service/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'StudentAngular';
  constructor(public webSocket: WebSocketService) {
  }
}
