import {FormControl, FormGroup, Validators} from '@angular/forms';


export class ValidStudent {
  public myForm: FormGroup;

  constructor(
    public studentId: number,
    firstName: string,
    lastName: string,
    years: number,
  ) {
    this.myForm = new FormGroup({
      firstName: new FormControl(firstName, [ Validators.minLength(3) , Validators.required]),
      lastName: new FormControl(lastName, [ Validators.minLength(3) , Validators.required]),
      years: new FormControl(years, [ Validators.min(0) , Validators.required])
    });
  }
}
