import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS,
  SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Zahtev } from 'src/app/models/zahtev';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { ZahtevValidatorService } from './zahtev-validator.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zahtev-form',
  templateUrl: './zahtev-form.component.html',
  styleUrls: ['./zahtev-form.component.sass']
})
export class ZahtevFormComponent implements AfterViewInit {

  constructor(
    private zahtevService: ZahtevService,
    private zahtevValidator: ZahtevValidatorService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  zahtevForm: FormGroup = new FormGroup({
    tipZahteva: new FormControl('', [Validators.required]),
    tipDostave: new FormControl('', [this.zahtevValidator.tipDostave()]),
    opisDostave: new FormControl('', [this.zahtevValidator.opisDostave()])
  });

  get dostava(): boolean{
    return this.zahtevForm.value.tipZahteva === 'dostava';
  }

  get ostalaDostava(): boolean{
    return this.dostava && this.zahtevForm.value.tipDostave === 'ostalo';
  }

  get prazniDetalji(): boolean{
    const parser = new DOMParser();
    const Detalji = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Detalji').item(0);
    return Detalji.textContent.trim() === '';
  }

  save(): void{
    if (!this.zahtevForm.valid){
      return;
    }
    if (this.prazniDetalji){
      this.snackBar.open(EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    const zahtev: Zahtev = this.zahtevForm.value;
    zahtev.detalji = Xonomy.harvest();
    this.savePending = true;
    this.zahtevService.save(zahtev).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Zahtev poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.zahtevForm.reset();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const detaljiXml = '<Detalji></Detalji>';
    const zahtevEditor = document.getElementById('zahtevEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, zahtevEditor, detaljiSpecifikacija);

    this.zahtevForm.get('tipZahteva').valueChanges.subscribe(
      () => {
        this.zahtevForm.get('tipDostave').updateValueAndValidity();
      }
    );
    this.zahtevForm.get('tipDostave').valueChanges.subscribe(
      () => {
        this.zahtevForm.get('opisDostave').updateValueAndValidity();
      }
    );
  }

}
