import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Student} from '../model/Student';

@Injectable({providedIn: 'root'})
export class StudentService {

  constructor(private http: HttpClient) {
  }

  getStudent(): Observable<Student[]> {
    return this.http.get<Student[]>('http://localhost:8180/restStudent');
  }

  updateStudent(student: Student): Observable<any> {
    return this.http.put('http://localhost:8180/restStudent', student);
  }

}
