import {Injectable} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {FormListMarks} from '../../model/FormListMarks';
import {Observable} from 'rxjs';
import {ValidStudent} from '../../model/ValidStudent';
import {HttpClient} from '@angular/common/http';
import {Student} from '../../model/Student';
import {SubjectBinder} from '../../model/SubjectBinder';
import {Subject} from '../../model/Subject';


@Injectable({providedIn: 'root'})
export class WebSocketService {
  private url = 'http://localhost:8180';
  private webSocketEndPoint = 'http://localhost:8180/ws';
  topic: string = '/topic/connect';
  public stompStudent: Stomp.Client;

  constructor(private http: HttpClient) {
    this.connect();
  }

  connect() {
    console.log('Initialize WebSocket Connection');
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompStudent = Stomp.over(ws);
    const ts = this;
    this.stompStudent.connect({}, function(frame) {

      ts.stompStudent.subscribe(ts.topic, function(sdkEvent: any) {
        ts.onMessageReceived(sdkEvent);
      });
      ts.stompStudent.subscribe('/topic/update', function(studentMessage) {
        console.log(JSON.parse(studentMessage.body));
      });

   //   ts.send('start');

      //this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  }

  onMessageReceived(message: any): void {
    console.log('Message Recieved from Server :: ' + JSON.stringify(message.body));
    // this.appComponent.handleMessage(JSON.stringify(message.body));
  }

  send(message: any): void {
    console.log('calling logout api via web socket');
    this.stompStudent.send('/app/hello', {}, JSON.stringify(message));
  }

  errorCallBack(error: any): void {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this.connect();
    }, 5000);
  }

  saveMarks(studentId: number, formListMarks: FormListMarks): void {

    this.http.put(this.url + `/save/marks/${studentId}`, formListMarks, {observe: 'response'}).subscribe();
  }

  updateStudent(student: ValidStudent): void {
    this.stompStudent.send('/app/update', {}, JSON.stringify(this.converterValidStudent(student)));
    // this.http.put(this.url, this.converterValidStudent(student), {observe: 'events'}).subscribe();
  }

  converterValidStudent(student: ValidStudent): PutStudent {
    return new PutStudent(
      student.studentId
      , student.myForm.controls['firstName'].value
      , student.myForm.controls['lastName'].value
      , student.myForm.controls['years'].value
    );
  }
}

export class PutStudent {
 //public subjectBinderMap: Map<number , SubjectBinder> = new Map<number, SubjectBinder>();
  constructor(public studentId: number,
              public firstName: string,
              public lastName: string,
              public years: number) {
  //  this.subjectBinderMap.set(0 , new SubjectBinder(1 , new Subject(1 , 'histor') , [] , 5));
  }
}
