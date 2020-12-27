import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.sass']
})
export class RegisterFormComponent implements OnInit {

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar
    ) { }

  registerPending = false;
  registerForm: FormGroup = new FormGroup({
    mejl: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    lozinka: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    prezime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    mesto: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ulica: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    broj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  register(): void{
    if (this.registerForm.invalid){
      return;
    }
    this.registerPending = true;
    this.userService.register(this.registerForm.value).subscribe(
      () => {
        this.registerPending = false;
        this.snackBar.open('Your request has been sent! Check your email.',
        SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.registerForm.reset();
      },
      () => {
        this.registerPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngOnInit(): void {
  }

}
