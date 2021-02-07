import {Component} from '@angular/core';
import {WebSocketService} from './shared/service/web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'StudentAngular';
  idClient = 2;

  constructor(public webSocket: WebSocketService) {
  }

  setId(): void {
    this.webSocket.idClient = this.idClient;
    this.webSocket.connect();
  }
}
