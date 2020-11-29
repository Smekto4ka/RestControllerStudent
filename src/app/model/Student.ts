import {SubjectBinder} from './SubjectBinder';

export class Student {
  constructor(
    public studentId: number,
    public firstName: string,
    public lastName: string,
    public years: number,
    public subjectBinderAll: SubjectBinder[]
  ) {
  }
}
