import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Obavestenje } from 'src/app/models/obavestenje';
import { ObavestenjeService } from 'src/app/services/obavestenje/obavestenje.service';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-obavestenje-form',
  templateUrl: './obavestenje-form.component.html',
  styleUrls: ['./obavestenje-form.component.sass']
})
export class ObavestenjeFormComponent implements AfterViewInit {

  constructor(
    private route: ActivatedRoute,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private obavestenjeService: ObavestenjeService
  ) { }

  obavestenjePending = false;
  obavestenjeForm: FormGroup = new FormGroup({
    mesto: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ulica: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    broj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kancelarija: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    datum: new FormControl('', [Validators.required]),
    pocetak: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kraj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kopija: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  posaljiObavestenje(): void{
    if (this.obavestenjeForm.invalid){
      return;
    }
    const obavestenje: Obavestenje = this.obavestenjeForm.value;
    obavestenje.detalji = Xonomy.harvest();
    this.obavestenjePending = true;
    this.obavestenjeService.save(this.route.snapshot.params.brojZahteva, obavestenje).subscribe(
      () => {
        this.obavestenjePending = false;
        this.snackBar.open('Obaveštenje uspešno poslato!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.obavestenjePending = false;
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
