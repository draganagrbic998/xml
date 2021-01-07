import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ZalbaValidatorService {

  constructor() { }

  brojOdluke(): ValidatorFn{
    return (control: AbstractControl): ValidationErrors => {
      let brojOdlukeValid = true;
      if (control.parent && control.parent.get('tipCutanja').value === 'nije postupio u celosti' && !control.value){
        brojOdlukeValid = false;
      }
      return brojOdlukeValid ? null : {brojOdlukeError: true};
    };
  }

}
