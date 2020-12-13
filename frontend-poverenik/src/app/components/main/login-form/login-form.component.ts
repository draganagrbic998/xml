import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass']
})
export class LoginFormComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  loginPending = false;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  login(): void{
    if (this.loginForm.invalid){
      return;
    }
    this.loginPending = true;
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
