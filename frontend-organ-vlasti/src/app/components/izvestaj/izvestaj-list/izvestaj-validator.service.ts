import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class IzvestajValidatorService {

  constructor() { }

  godina(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let godinaValid = true;
      if (control.value >= new Date().getFullYear().toString()){
        godinaValid = false;
      }
      return godinaValid ? null : {godinaError: true};
    };
  }
}
