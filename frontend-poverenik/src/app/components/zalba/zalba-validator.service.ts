import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ZalbaValidatorService {

  constructor() { }

  adresa(): ValidatorFn{
    return (control: AbstractControl): null | ValidationErrors => {
      const adresaValid = control.value.split(' ').length === 3;
      return adresaValid ? null : {adresaError: true};
    };
  }

}
