import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-zahtev-details',
  templateUrl: './zahtev-details.component.html',
  styleUrls: ['./zahtev-details.component.sass']
})
export class ZahtevDetailsComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  @Input() zahtev: ZahtevDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  ngOnInit(): void {
  }

}
