import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Zahtev } from 'src/app/models/zahtev';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zahtev-form',
  templateUrl: './zahtev-form.component.html',
  styleUrls: ['./zahtev-form.component.sass']
})
export class ZahtevFormComponent implements AfterViewInit {

  constructor(
    private zahtevService: ZahtevService,
    private snackBar: MatSnackBar,
    private xonomyService: XonomyService
  ) { }

  zahtevPending = false;
  zahtevForm: FormGroup = new FormGroup({
    tipZahteva: new FormControl('obavestenje', [Validators.required]),
    tipDostave: new FormControl('posta', [Validators.required]),
    opisDostave: new FormControl('', [Validators.required]),
  });

  get dostava(): boolean{
    return this.zahtevForm.value.tipZahteva === 'dostava';
  }

  get ostalaDostava(): boolean{
    return this.dostava && this.zahtevForm.value.tipDostave === 'ostalo';
  }

  refreshForm(): void{
    if (!this.dostava){
      this.zahtevForm.get('tipDostave').setErrors(null);
      this.zahtevForm.get('opisDostave').setErrors(null);
    }
    if (!this.ostalaDostava){
      this.zahtevForm.get('opisDostave').setErrors(null);
    }
  }

  posaljiZahtev(): void{
    this.refreshForm();
    const zahtev: Zahtev = this.zahtevForm.value;
    zahtev.detalji = Xonomy.harvest();
    this.zahtevPending = true;
    this.zahtevService.save(zahtev).subscribe(
      () => {
        this.zahtevPending = false;
        this.snackBar.open('Zahtev uspešno poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.zahtevPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  test(): void{
    this.zahtevService.list().subscribe(
      (zahtevi: ZahtevDTO[]) => {
        console.log(zahtevi);
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
