import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LOGIN_PATH } from 'src/app/constants/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ){ }

  canActivate(): boolean {
    console.log(this.router.url);

    if (!this.authService.getUser()){
      this.router.navigate([LOGIN_PATH]);
      return false;
    }
    return true;
  }

}
