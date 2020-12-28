import { Injectable } from '@angular/core';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ZahtevValidatorService {

  constructor() { }

  tipDostave(): ValidatorFn{
    return (control: AbstractControl): null | ValidationErrors => {
      let tipDostaveValid = true;
      if (control.parent && control.parent.get('tipZahteva').value === 'dostava' && !control.value){
        tipDostaveValid = false;
      }
      return tipDostaveValid ? null : {tipDostaveError: true};
    };
  }

  opisDostave(): ValidatorFn{
    return (control: AbstractControl): null | ValidationErrors => {
      let opisDostaveValid = true;
      if (control.parent && control.parent.get('tipZahteva').value === 'dostava' &&
      control.parent.get('tipDostave').value === 'ostalo' && !control.value){
        opisDostaveValid = false;
      }
      return opisDostaveValid ? null : {opisDostaveError: true};
    };
  }

}
