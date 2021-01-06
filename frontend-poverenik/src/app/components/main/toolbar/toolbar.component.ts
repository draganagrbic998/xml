import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LOGIN_PATH, REGISTER_PATH } from 'src/app/constants/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.sass']
})
export class ToolbarComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  get imePrezime(): string{
    return `${this.authService.getUser()?.ime} ${this.authService.getUser()?.prezime}`;
  }

  get auth(): boolean{
    return this.router.url.includes(LOGIN_PATH) || this.router.url.includes(REGISTER_PATH);
  }

  get login(): boolean{
    return this.router.url.includes(LOGIN_PATH);
  }

  signOut(): void{
    this.authService.deleteUser();
    this.router.navigate([LOGIN_PATH]);
  }

  ngOnInit(): void {
  }

}
