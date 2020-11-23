import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {PutStudent} from '../model/PutStudent';

@Component({
  selector: 'app-post-student',
  templateUrl: './post-student.component.html',
  styleUrls: ['./post-student.component.scss']
})
export class PostStudentComponent implements OnInit {


  public student: PutStudent;

  constructor(private studentService: StudentService) {
    this.student = new PutStudent(0, '', '', 0);
  }


  ngOnInit(): void {
  }

  postStudent() {
    this.studentService.postStudent(this.student);
  }
}
