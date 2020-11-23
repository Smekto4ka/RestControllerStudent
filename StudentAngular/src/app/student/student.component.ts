import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {HttpEvent, HttpEventType} from '@angular/common/http';
import {PutStudent} from '../model/PutStudent';

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
    this.studentService.getStudent().subscribe(student => this.studentObj = student
      .sort((st1, st2) => st1.studentId - st2.studentId).map(stud => new StudentObj(stud)));
  }

  deleteStudent(id: number) {

    this.studentService.deleteStudent(id).subscribe(resp => {
      if (resp.ok) {
        const indx = this.getIndex(id);
        this.studentObj.splice(indx, 1);
      }
    });
  }

  saveMarks(id: number) {
    console.log(this.studentObj[this.getIndex(id)].arraysMarks);
  }

  updateStudent(id: number) {
    const indx = this.getIndex(id);
    const studObj = this.studentObj[indx];
    this.studentService.updateStudent( studObj.putStudent)
      .subscribe((event: HttpEvent<any>) => {
        studObj.update();
        if (event.type === HttpEventType.Response && event.ok) {
          if (event.body != null) {
            this.studentObj[indx].student = event.body;
          }
        }
      });
  }

  getIndex(id: number): number {
    return this.studentObj.findIndex(student => student.student.studentId === id);
  }

}


export class StudentObj {
  public visibleInfo = false;
  public visibleUpdate = false;
  public putStudent: PutStudent;
  public visibleWindowsMarks = false;
  public arraysMarks = new Array(1);


  constructor(public student: Student) {
    this.putStudent = new PutStudent(student.studentId , '', '', 0);
  }

  update() {
    this.putStudent.lastName = '';
    this.putStudent.firstName = '';
    this.putStudent.years = 0;
  }

  public isVisibleInfoStudent() {
    this.visibleInfo = !this.visibleInfo;
  }

  public isVisibleUpdateStudent() {

    this.visibleUpdate = !this.visibleUpdate;

  }

  public isVisibleWindowsMarks() {
    this.visibleWindowsMarks = !this.visibleWindowsMarks;
  }

  public updateArraysMarks(event: string) {
    console.log(event);
    this.arraysMarks = new Array(Number.parseInt(event));
  }
}
