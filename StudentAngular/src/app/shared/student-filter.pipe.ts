import {Pipe, PipeTransform} from '@angular/core';
import {WrapperStudent} from '../student/student.component';


@Pipe({
  name: 'filterStudentById'
})
export class StudentFilterPipe implements PipeTransform {
  transform(student: WrapperStudent[], minimum: string, maximum: string): WrapperStudent[] {
    if (maximum === null) {
      maximum = '';
    }
    if (minimum === null) {
      minimum = '';
    }
    if (minimum !== '' && maximum !== '') {
      return student.filter(stud => stud.student.studentId >= Number(minimum) && stud.student.studentId <= Number(maximum));
    }
    if (minimum !== '' && maximum === '') {
      return student.filter(stud => stud.student.studentId >= Number(minimum));
    }
    if (minimum === '' && maximum !== '') {
      return student.filter(stud => stud.student.studentId <= Number(maximum));
    }
    return student;
  }

}
