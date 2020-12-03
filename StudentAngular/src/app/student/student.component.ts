import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {HttpEvent, HttpEventType} from '@angular/common/http';
import {ValidStudent} from '../model/ValidStudent';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {FormListMarks} from '../model/FormListMarks';
import {PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  studentObj: StudentObj[] = [];
  nameSubject: string[] = [];
  minimumId: string = null;
  maximumId: string = null;

  currentPage = 1;
  itemsPerPage = 5;
  lengthPage = this.studentObj.length;

  constructor(private studentService: StudentService) {
  }


  ngOnInit(): void {
    this.studentService.getStudent().subscribe(student => this.studentObj = student
      .sort((st1, st2) => st1.studentId - st2.studentId).map(stud => new StudentObj(stud)));
    this.studentService.getNameSubject().subscribe(name => this.nameSubject = name);
  }

  @ViewChildren('updatePage') updatePage: QueryList<any>;


  setSettingsPage(event: PageEvent) {
    this.itemsPerPage = event.pageSize;
    this.currentPage = event.pageIndex + 1;
  }

  log(event: any) {
    console.log(this.updatePage);
  }

  deleteStudent(id: number) {

    this.studentService.deleteStudent(id).subscribe(resp => {
      if (resp.ok) {
        const indx = this.getIndex(id);
        this.studentObj.splice(indx, 1);
      }
    });
  }


  saveMarks(id: number, name: string) {

    this.studentService.saveMarks(id, new FormListMarks(name, this.studentObj[this.getIndex(id)].marksForm.value['marks']))
      .subscribe((event: HttpEvent<Student>) => {
        if (event.type === HttpEventType.Response && event.ok) {
          if (event.body != null) {

            this.studentObj[this.getIndex(event.body.studentId)].student = event.body;

          }
        }
      });
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
  public putStudent: ValidStudent;
  public marksForm: FormGroup;

  constructor(public student: Student) {
    this.putStudent = new ValidStudent(student.studentId, '', '', 0);
    this.marksForm = new FormGroup({
      marks: new FormArray([
        new FormControl(0, [Validators.required, Validators.max(5), Validators.min(0)])
      ])
    });
  }

  public updateArraysMarks(event: string) {
    const value = Number.parseInt(event);
    const length = (<FormArray> this.marksForm.controls['marks']).length;
    if (length - value > 0) {
      for (let i = length; i >= value; i = i - 1) {
        (<FormArray> this.marksForm.controls['marks']).removeAt(i);
      }
    } else {
      for (let i = 0; i < value - length; i = i + 1) {
        (<FormArray> this.marksForm.controls['marks'])
          .push(new FormControl(0, [Validators.required, Validators.max(5), Validators.min(0)]));
      }
    }
  }
}
