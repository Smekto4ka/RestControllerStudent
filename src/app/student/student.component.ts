import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {HttpEvent, HttpEventType} from '@angular/common/http';
import {ValidStudent} from '../model/ValidStudent';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {NgModel} from '@angular/forms';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  public studentObj: StudentObj[] = [];
  public nameSubject: string[];
public listItems: Array<string> = ['Baseball', 'Basketball', 'Cricket', 'Field Hockey', 'Football', 'Table Tennis', 'Tennis', 'Volleyball'];
  constructor(private studentService: StudentService) {
  }


  ngOnInit(): void {
    this.studentService.getStudent().subscribe(student => this.studentObj = student
      .sort((st1, st2) => st1.studentId - st2.studentId).map(stud => new StudentObj(stud)));
    this.studentService.getNameSubject().subscribe(name => this.nameSubject = name);
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
    console.log(this.studentObj[this.getIndex(id)].marksForm.value['marks']);

  }

  updateStudent(id: number) {
    const indx = this.getIndex(id);
    const studObj = this.studentObj[indx];
    this.studentService.updateStudent(studObj.putStudent)
      .subscribe((event: HttpEvent<any>) => {

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
  public putStudent: ValidStudent;
  public visibleWindowsMarks = false;
  public length = 1;
  public marksForm: FormGroup;

  constructor(public student: Student) {
    this.putStudent = new ValidStudent(student.studentId, '', '', 0);
    this.marksForm = new FormGroup({
      marks: new FormArray([
        new FormControl(0, [Validators.required, Validators.max(5), Validators.min(0)])
      ])
    });
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
    const value = Number.parseInt(event);
    this.length = (<FormArray> this.marksForm.controls['marks']).length;
    if (this.length - value > 0) {
      for (let i = this.length; i >= value; i = i - 1) {
        (<FormArray> this.marksForm.controls['marks']).removeAt(i);
      }
    } else {
      for (let i = 0; i < value - this.length; i = i + 1) {
        (<FormArray> this.marksForm.controls['marks'])
          .push(new FormControl(0, [Validators.required, Validators.max(5), Validators.min(0)]));
      }
    }
  }
}
