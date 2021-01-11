import {SubjectBinder} from './SubjectBinder';

export class Student {
  constructor(
    public studentId: number,
    public firstName: string,
    public lastName: string,
    public years: number,
    //TODO
    public subjectBinderAll: SubjectBinder[]
  ) {
  }
}
