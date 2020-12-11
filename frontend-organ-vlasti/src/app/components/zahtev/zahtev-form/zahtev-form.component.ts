import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

@Component({
  selector: 'app-zahtev-form',
  templateUrl: './zahtev-form.component.html',
  styleUrls: ['./zahtev-form.component.sass']
})
export class ZahtevFormComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService,
    private snackBar: MatSnackBar
  ) { }

  zahtevPending = false;
  zahtevForm: FormGroup = new FormGroup({
    tipUvida: new FormControl('posedovanje'),
    tipDostave: new FormControl('posta'),
    opisDostave: new FormControl(''), // dodaj validaciju ako je odabran 'ostalo' tip dostave
    detalji: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    prezime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    mesto: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ulica: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    broj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kontakt: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  get dostavaOdabrana(): boolean{
    return this.zahtevForm.value.tipUvida === 'dostava';
  }

  get ostalaDostavaOdabrana(): boolean{
    return this.zahtevForm.value.tipDostave === 'ostalo';
  }

  posaljiZahtev(): void{
    if (this.zahtevForm.invalid){
      return;
    }
    this.zahtevPending = true;
    this.zahtevService.save(this.zahtevForm.value).subscribe(
      () => {
        this.zahtevPending = false;
        this.snackBar.open('Zahtev je uspešno poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.zahtevPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngOnInit(): void {
  }

}
