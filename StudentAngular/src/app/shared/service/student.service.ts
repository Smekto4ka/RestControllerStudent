import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Student} from '../../model/Student';
import {ValidStudent} from '../../model/ValidStudent';
import {FormListMarks} from '../../model/FormListMarks';


@Injectable({providedIn: 'root'})
export class StudentService {
  private url = 'http://localhost:8180/restStudent';

  constructor(private http: HttpClient) {
  }

  saveMarks(studentId: number, formListMarks: FormListMarks): Observable<any> {

    return this.http.put(this.url + `/save/marks/${studentId}`, formListMarks, {observe: 'response'});
  }

  getNameSubject(): Observable<any> {
    return this.http.get(this.url + '/subject');
  }

  getStudent(): Observable<Student[]> {
    return this.http.get<Student[]>(this.url);
  }

  updateStudent(student: ValidStudent) {
    return this.http.put(this.url, this.converterValidStudent(student), {observe: 'events'});
  }

  postStudent(student: ValidStudent) {

    return this.http.post(this.url, this.converterValidStudent(student)).subscribe();
  }

  deleteStudent(studentId: number) {
    return this.http.delete(this.url + `/${studentId}`, {observe: 'response'});

  }

  converterValidStudent(student: ValidStudent) {
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
