import {Pipe, PipeTransform} from '@angular/core';
import {StudentObj} from '../../student/student.component';


@Pipe({
  name: 'filterStudentById'
})
export class StudentFilterPipe implements PipeTransform {
  transform(student: StudentObj[], minimum: string, maximum: string): StudentObj[] {
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
