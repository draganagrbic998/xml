import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user/user.service';
import { UserValidatorService } from 'src/app/validators/user-validator/user-validator.service';

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.sass']
})
export class RegisterFormComponent implements OnInit {

  constructor(
    private userService: UserService,
    private userValidator: UserValidatorService,
    private snackBar: MatSnackBar
    ) { }

  registerPending = false;
  registerForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))],
    /*[this.formValidator.hasEmail(null)]*/),
    password: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    passwordConfirmation: new FormControl('', /*[this.formValidator.passwordConfirmed()]*/),
    ime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    prezime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    mesto: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ulica: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    broj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  register(): void{
    /*
    if (this.registerForm.invalid){
      return;
    }
    this.registerPending = true;*/
    /*
    this.userService.register(this.registerForm.value).subscribe(
      () => {
        this.registerPending = false;
        this.snackBar.open('Your request has been sent! Check your email.',
        SNACKBAR_CLOSE, SUCCESS_SNACKBAR_OPTIONS);
        this.registerForm.reset();
      },
      () => {
        this.registerPending = false;
        this.snackBar.open(ERROR_MESSAGE, SNACKBAR_CLOSE, ERROR_SNACKBAR_OPTIONS);
      }
    );*/
  }

  ngOnInit(): void {
    this.registerForm.get('password').valueChanges.subscribe(
      () => {
        this.registerForm.get('passwordConfirmation').updateValueAndValidity();
      }
    );
  }

}
