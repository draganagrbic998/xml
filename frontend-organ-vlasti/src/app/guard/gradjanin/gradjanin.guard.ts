import { Injectable } from '@angular/core';
import { CanActivate,  Router } from '@angular/router';
import { GRADJANIN } from 'src/app/constants/roles';
import { LOGIN_PATH } from 'src/app/constants/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class GradjaninGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ){ }

  canActivate(): boolean {
      if (this.authService.getUser()?.uloga !== GRADJANIN){
        this.router.navigate([LOGIN_PATH]);
        return false;
      }
      return true;
  }

}
