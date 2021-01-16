import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {ValidStudent} from '../model/ValidStudent';
import {WebSocketService} from '../shared/service/web-socket.service';

@Component({
  selector: 'app-post-student',
  templateUrl: './post-student.component.html',
  styleUrls: ['./post-student.component.scss']
})
export class PostStudentComponent implements OnInit {


  public student: ValidStudent;

  constructor(private studentService: WebSocketService) {
    if (!studentService.stompStudent.connected) {
      studentService.connect();
    }
    this.student = new ValidStudent(0, '', '', 0);
  }


  ngOnInit(): void {
  }

  postStudent(): void {
    console.log(this.student);
    this.studentService.postStudent(this.student);
  }
}
