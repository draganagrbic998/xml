import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';
import { ZalbaValidatorService } from '../zalba-validator.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zalba-cutanje-form',
  templateUrl: './zalba-cutanje-form.component.html',
  styleUrls: ['./zalba-cutanje-form.component.sass']
})
export class ZalbaCutanjeFormComponent implements AfterViewInit {

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
    tipCutanja: new FormControl('nije postupio', [Validators.required]),
  });

  save(): void{
    if (this.zalbaForm.invalid){
      return;
    }
    const zalba: ZalbaCutanje = this.zalbaForm.value;
    zalba.detalji = Xonomy.harvest();
    this.savePending = true;
    this.zalbaService.saveZalbaCutanje(zalba).subscribe(
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