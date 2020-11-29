import {Pipe, PipeTransform} from '@angular/core';
import {StudentObj} from '../../student/student.component';

@Pipe({
  name: 'filterStudentById'
})
export class StudentFilterPipe implements PipeTransform {
  transform(student: StudentObj[], minimum: string, maximum: string): StudentObj[] {
    if (minimum !== null && maximum !== null) {
      return student.filter(stud => stud.student.studentId >= Number.parseInt(minimum) && stud.student.studentId <= Number.parseInt(maximum));
    }
    if (minimum !== null && maximum === null) {
      return student.filter(stud => stud.student.studentId >= Number.parseInt(minimum));
    }
    if (minimum === null && maximum !== null) {
      return student.filter(stud => stud.student.studentId <= Number.parseInt(maximum));
    }
    return student;
  }

}
