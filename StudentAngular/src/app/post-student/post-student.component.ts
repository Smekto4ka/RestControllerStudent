import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';

@Component({
  selector: 'app-post-student',
  templateUrl: './post-student.component.html',
  styleUrls: ['./post-student.component.scss']
})
export class PostStudentComponent implements OnInit {

  constructor(private studentService: StudentService) {
  }

  lastName = '';
  firstName = '';
  years = 0;

  ngOnInit(): void {
  }

  postStudent() {
    this.studentService.postStudent(new Student(0, this.firstName, this.lastName, this.years, []));
  }
}
