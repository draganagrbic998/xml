import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZalbaOdluka } from 'src/app/models/zalba-odluka';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';
import { ZalbaValidatorService } from '../zalba-validator.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zalba-odluka-form',
  templateUrl: './zalba-odluka-form.component.html',
  styleUrls: ['./zalba-odluka-form.component.sass']
})
export class ZalbaOdlukaFormComponent implements AfterViewInit {

  constructor(
    private zalbaService: ZalbaService,
    private zalbaValidator: ZalbaValidatorService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  zalbaForm: FormGroup = new FormGroup({
    naziv: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    sediste: new FormControl('', [Validators.required, this.zalbaValidator.adresa()]),
    datumZahteva: new FormControl('', [Validators.required]),
    brojOdluke: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    datumOdluke: new FormControl('', [Validators.required])
  });

  save(): void{
    if (this.zalbaForm.invalid){
      return;
    }
    const zalba: ZalbaOdluka = this.zalbaForm.value;
    zalba.detalji = Xonomy.harvest();
    this.savePending = true;
    this.zalbaService.saveZalbaOdluka(zalba).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Zalba uspešno poslata!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const detaljiXml = '<Detalji></Detalji>';
    const detaljiEditor = document.getElementById('detaljiEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, detaljiEditor, detaljiSpecifikacija);
  }

}