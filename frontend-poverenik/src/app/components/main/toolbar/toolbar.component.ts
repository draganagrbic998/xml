import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LOGIN_PATH, ZALBA_CUTANJE_FORM, ZALBA_ODLUKA_FORM } from 'src/app/constants/router';
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

  odjava(): void{
    this.authService.deleteUser();
    this.router.navigate([LOGIN_PATH]);
  }

  get login(): boolean{
    return this.router.url.includes(LOGIN_PATH);
  }

  ngOnInit(): void {
  }

}
