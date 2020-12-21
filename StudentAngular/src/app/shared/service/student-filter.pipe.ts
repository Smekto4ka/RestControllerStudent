import {Pipe, PipeTransform} from '@angular/core';
import {StudentObj} from '../../student/student.component';


@Pipe({
  name: 'filterStudentById'
})
export class StudentFilterPipe implements PipeTransform {
  transform(student: StudentObj[], minimum: number, maximum: number): StudentObj[] {

    if (minimum !== null && maximum !== null) {
      return student.filter(stud => stud.student.studentId >= minimum && stud.student.studentId <= maximum);
    }
    if (minimum !== null && maximum === null) {
      return student.filter(stud => stud.student.studentId >= minimum);
    }
    if (minimum === null && maximum !== null) {
      return student.filter(stud => stud.student.studentId <= maximum);
    }
    return student;
  }

}
