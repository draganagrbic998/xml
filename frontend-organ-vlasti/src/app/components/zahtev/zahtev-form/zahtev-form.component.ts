import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-zahtev-form',
  templateUrl: './zahtev-form.component.html',
  styleUrls: ['./zahtev-form.component.sass']
})
export class ZahtevFormComponent implements OnInit {

  constructor(
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
    /*
    this.userService.login(this.loginForm.value).subscribe(
      (user: User) => {
        this.loginPending = false;
        this.authService.saveUser(user);
        this.router.navigate(['/']);
      },
      () => {
        this.loginPending = false;
        this.snackBar.open(ERROR_MESSAGE, SNACKBAR_CLOSE, ERROR_SNACKBAR_OPTIONS);
      }
    );*/
  }

  ngOnInit(): void {
  }

}
