import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Student} from '../../model/Student';
import {catchError} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class StudentService {
  private url = 'http://localhost:8180/restStudent';

  constructor(private http: HttpClient) {
  }

  getStudent(): Observable<Student[]> {
    return this.http.get<Student[]>(this.url);
  }

  updateStudent(student: Student): Observable<any> {
    return this.http.put(this.url, student).subscribe();
  }

  postStudent(student: Student): Observable<any> {
    return this.http.post(this.url, student).subscribe();
  }

}