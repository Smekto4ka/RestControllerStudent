import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpEventType, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Student} from '../../model/Student';
import {catchError} from 'rxjs/operators';
import {StudentObj} from '../../student/student.component';
import {PutStudent} from '../../model/PutStudent';

@Injectable({providedIn: 'root'})
export class StudentService {
  private url = 'http://localhost:8180/restStudent';


  constructor(private http: HttpClient) {
  }

  getStudent(): Observable<Student[]> {
    return this.http.get<Student[]>(this.url);
  }

  updateStudent(student: PutStudent) {
    return this.http.put(this.url, student, {observe: 'events'});
  }

  postStudent(student: PutStudent) {

    return this.http.post(this.url, student).subscribe();
  }

  deleteStudent(studentId: number) {
    return this.http.delete(this.url + `/${studentId}`, {observe: 'response'});

  }
}
