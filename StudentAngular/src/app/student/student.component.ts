import {Component, OnInit} from '@angular/core';
import {StudentService} from '../shared/service/student.service';
import {Student} from '../model/Student';
import {HttpEvent, HttpEventType} from '@angular/common/http';
import {ValidStudent} from '../model/ValidStudent';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {FormListMarks} from '../model/FormListMarks';
import {PageEvent} from '@angular/material/paginator';
import {WebSocketService} from '../shared/service/web-socket.service';
import {SubjectBinder} from '../model/SubjectBinder';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {

  studentObj: WrapperStudent[];
  nameSubject: string[] = [];

  minimumId = '';
  maximumId = '';

  currentPage = 1;
  itemsPerPage = 5;


  constructor(private studentService: StudentService, private webSocket: WebSocketService) {
    webSocket.studentComponent = this;
    if (webSocket.stompStudent.connected) {
      this.sendStudent();
    }
  }


  ngOnInit(): void {
    this.studentService.getNameSubject().subscribe(name => this.nameSubject = name);
  }

  sendStudent() {
    this.webSocket.stompStudent.send('/app/all', {}, JSON.stringify(this.webSocket.idClient));
  }


  public initStudent(event: any): void {
    const students: Student[] = JSON.parse(event.body);
    this.studentObj = students.sort((st1, st2) => st1.studentId - st2.studentId).map(stud => new WrapperStudent(stud));
  }

  buttonClicker() {
    /* for(const stud of this.studentObj){
       console.log( stud.student.subjectBinderMap[1]);
     }*/

  }


  setSettingsPage(event: PageEvent): void {
    this.itemsPerPage = event.pageSize;
    this.currentPage = event.pageIndex + 1;
  }

  /* deleteStudent(id: number): void {

     this.studentService.deleteStudent(id).subscribe(resp => {
       if (resp.ok) {
         const indx = this.getIndex(id);
         this.studentObj.splice(indx, 1);
       }
     });
   }*/
  deleteStudent(id: number): void {
    this.webSocket.deleteStudent(id);
  }

  deleteStudentBySubscription(jsonStudentID: any): void {
    console.log(JSON.parse(jsonStudentID.body));
    const studentID: number = JSON.parse(jsonStudentID.body);
    const indx = this.getIndex(studentID);
    if (indx >= 0) {
      this.studentObj.splice(indx, 1);
    }
  }

  /*  saveMarks(id: number, name: string): void {

       this.studentService.saveMarks(id, new FormListMarks(name, this.studentObj[this.getIndex(id)].marksFormArray().getRawValue()))
         .subscribe((event: HttpEvent<Student>) => {
           if (event.type === HttpEventType.Response && event.ok) {
             if (event.body != null) {

               this.studentObj[this.getIndex(event.body.studentId)].student = event.body;

             }
           }
         });
     }*/

  saveMarks(id: number, name: string): void {
    this.webSocket.saveMarks(new FormListMarks(id, name, this.studentObj[this.getIndex(id)].marksFormArray().getRawValue()));
  }


  /*updateStudent(id: number): void {
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
  }*/

  updateStudentBySubscription(jsonStudent: any): void {
    const student: Student = JSON.parse(jsonStudent.body);
    /*    for (const value of student.subjectBinderMap) {
          console.log(value[1]);
        }*/
    //console.log(typeof student.subjectBinderMap);
    const indx = this.getIndex(student.studentId);
    if (indx >= 0) {
      const studentWrapper = this.studentObj[indx];
      studentWrapper.student = student;
    } else {
      this.studentObj.push(new WrapperStudent(student));
    }
  }

  updateStudent(id: number): void {
    const indx = this.getIndex(id);
    const studObj = this.studentObj[indx];
    this.webSocket.updateStudent(studObj.putStudent);
  }

  getIndex(id: number): number {
    return this.studentObj.findIndex(student => student.student.studentId === id);
  }

}


export class WrapperStudent {
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

  public marksFormArray(): FormArray {
    return (<FormArray> this.marksForm.get('marks'));
  }

  public updateArraysMarks(event: string): void {

    const value = Number(event);
    const length = this.marksFormArray().length;
    if (length - value > 0) {
      for (let i = length; i >= value; i = i - 1) {
        this.marksFormArray().removeAt(i);
      }
    } else {
      for (let i = 0; i < value - length; i = i + 1) {
        this.marksFormArray()
          .push(new FormControl(0, [Validators.required, Validators.max(5), Validators.min(0)]));
      }
    }
  }
}
