import {Subject} from './Subject';
import {Marks} from './Marks';

export class SubjectBinder {
  constructor(
    public binderId: number,
    public subject: Subject,
    public marksList: Marks[],
    public average: number
  ) {
  }
}
