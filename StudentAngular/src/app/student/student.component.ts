import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  public studentObj: StudentObj[] = [];

  constructor(private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.studentService.getStudent().subscribe(student => this.studentObj = student.map(stud => new StudentObj(stud)));
  }

  isVisibleInfoStudent(id: number) {
    const indx = this.getIndex(id);
    this.studentObj[indx].visibleInfo = !this.studentObj[indx].visibleInfo;
  }

  isVisibleUpdateStudent(id: number) {
    const indx = this.getIndex(id);
    this.studentObj[indx].visibleUpdate = !this.studentObj[indx].visibleUpdate;

  }

  updateStudent(id: number) {
    const indx = this.getIndex(id);
    this.studentService.updateStudent(this.studentObj[indx].student);
  }

  getIndex(id: number): number {
    return this.studentObj.findIndex(student => student.student.studentId === id);
  }

}

export class StudentObj {
  public visibleInfo = false;
  public visibleUpdate = false;

  constructor(public student: Student) {
  }

}