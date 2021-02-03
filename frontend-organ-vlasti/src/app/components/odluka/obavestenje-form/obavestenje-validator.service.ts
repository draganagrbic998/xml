import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ObavestenjeValidatorService {

  constructor() { }

  datumUvida(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let datumUvidaValid = true;
      if (control.value < new Date()){
        datumUvidaValid = false;
      }
      return datumUvidaValid ? null : {datumUvidaError: true};
    };
  }

}
