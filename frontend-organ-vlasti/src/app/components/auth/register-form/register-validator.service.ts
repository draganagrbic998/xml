import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class RegisterValidatorService {

  constructor() { }

  ponovljenaLozinka(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let ponovljenaLozinkaValid = true;
      if (control.parent && control.parent.get('lozinka').value !== control.value){
        ponovljenaLozinkaValid = false;
      }
      return ponovljenaLozinkaValid ? null : {ponovljenaLozinkaError: true};
    };
  }

}
