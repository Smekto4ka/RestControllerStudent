import {Component} from '@angular/core';
import {WebSocketService} from './shared/service/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'StudentAngular';
  idClient = 1;

  constructor(public webSocket: WebSocketService) {
  }

  setId() {
    this.webSocket.idClient = this.idClient;
    this.webSocket.connect();
  }
}
