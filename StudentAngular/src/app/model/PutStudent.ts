import {FormControl, FormGroup, Validators} from '@angular/forms';

export class PutStudent {
  public myForm: FormGroup;


  constructor(
    public studentId: number,
    firstName: string,
    lastName: string,
    years: number,
  ) {
    this.myForm = new FormGroup({
      firstName: new FormControl(firstName, Validators.minLength(3)),
      lastName: new FormControl(lastName, Validators.required),
      years: new FormControl(years, Validators.min(0))
    });
  }

}
