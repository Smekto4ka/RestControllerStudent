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
import {StudentComponent} from '../../student/student.component';
import {deprecate} from 'util';
import {BodyAndIdClient} from '../body-and-id-client';
import {PostStudentComponent} from '../../post-student/post-student.component';


@Injectable({providedIn: 'root'})
export class WebSocketService {
  private webSocketEndPoint = 'http://localhost:8180/ws';
  public stompStudent: Stomp.Client;
  public studentComponent: StudentComponent;
  public idClient: number;
  public postStudentComponent: PostStudentComponent;

  constructor() {

  }


  connect(): void {
    console.log('Initialize WebSocket Connection');
    const ws = new SockJS(this.webSocketEndPoint);
    this.stompStudent = Stomp.over(ws);
    const ts = this;
    this.stompStudent.connect({}, (frame) => {
      ts.webSocketSubscription();
      ts.studentComponentSubscription();


      ts.stompStudent.subscribe('/user/queue/post', (event) => {
        if (ts.postStudentComponent) {
          ts.postStudentComponent.newStudentEvent(event);
        }
      });


      //this.stompClient.reconnect_delay = 2000;
    }, this.errorCallBack);
  }


  webSocketSubscription(): void {
    const ts = this;
    ts.send('dop client');
    this.stompStudent.subscribe('/topic/connect', (sdkEvent: any) => {
      ts.onMessageReceived(sdkEvent);
    });
  }


  studentComponentSubscription(): void {
    const ts = this;
    ts.stompStudent.subscribe('/topic/update', (sdkEvent: any) => {
      if (ts.studentComponent) {
        ts.studentComponent.updateStudentBySubscription(sdkEvent);
      }
    });
    ts.stompStudent.subscribe('/topic/delete', (sdkEvent: any) => {
      if (ts.studentComponent) {
        ts.studentComponent.deleteStudentBySubscription(sdkEvent);
      }
    });
    ts.stompStudent.subscribe('/user/queue/student/all',
      (event) => ts.studentComponent.initStudent(event));
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

  saveMarks(formListMarks: FormListMarks): void {
    this.stompStudent.send('/app/save/marks', {}, JSON.stringify(formListMarks));
    // this.http.put(this.url + `/save/marks/${studentId}`, formListMarks, {observe: 'response'}).subscribe();
  }

  updateStudent(student: ValidStudent): void {
    this.stompStudent.send('/app/update', {}, JSON.stringify(this.converterValidStudent(student)));
    // this.http.put(this.url, this.converterValidStudent(student), {observe: 'events'}).subscribe();
  }

  deleteStudent(studentId: number): void {
    this.stompStudent.send('/app/delete', {}, JSON.stringify(studentId));
    // return this.http.delete(this.url + `/${studentId}`, {observe: 'response'});

  }

  postStudent(student: ValidStudent): void {
    this.stompStudent.send('/app/postStudent', {}, JSON.stringify(new BodyAndIdClient(this.converterValidStudent(student), this.idClient)));
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
  constructor(public studentId: number,
              public firstName: string,
              public lastName: string,
              public years: number) {
  }
}
