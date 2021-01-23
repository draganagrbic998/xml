import { Location } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Obavestenje } from 'src/app/models/obavestenje';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-obavestenje-form',
  templateUrl: './obavestenje-form.component.html',
  styleUrls: ['./obavestenje-form.component.sass']
})
export class ObavestenjeFormComponent implements AfterViewInit {

  constructor(
    private odlukaService: OdlukaService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  savePending = false;
  obavestenjeForm: FormGroup = new FormGroup({
    datumUvida: new FormControl('', [Validators.required]),
    pocetak: new FormControl('', [Validators.required, Validators.pattern(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)]),
    kraj: new FormControl('', [Validators.required, Validators.pattern(/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/)]),
    kancelarija: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kopija: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)])
  });

  save(): void{
    if (this.obavestenjeForm.invalid){
      return;
    }
    const obavestenje: Obavestenje = this.obavestenjeForm.value;
    obavestenje.detalji = Xonomy.harvest();
    this.savePending = true;
    this.odlukaService.saveObavestenje(this.route.snapshot.params.brojZahteva, obavestenje).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Obaveštenje poslato!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.location.back();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }


  ngAfterViewInit(): void{
    const detaljiXml = '<Detalji></Detalji>';
    const obavestenjeEditor = document.getElementById('obavestenjeEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, obavestenjeEditor, detaljiSpecifikacija);
  }

}

