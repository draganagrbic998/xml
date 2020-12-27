import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-zahtev-details',
  templateUrl: './zahtev-details.component.html',
  styleUrls: ['./zahtev-details.component.sass']
})
export class ZahtevDetailsComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private zahtevService: ZahtevService,
    private router: Router
  ) { }

  @Input() zahtev: ZahtevDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  prezumiPdf(broj: string): void{
    this.zahtevService.getPdf(broj).subscribe(
      () => {
        console.log('prosla');
      }
    );
  }

  openPdf(): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${this.zahtev.broj}/pdf`, '_blank');
  }

  openHtml(): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${this.zahtev.broj}/html`, '_blank');
  }

  dodajObavestenje(): void{
    this.router.navigate([`/obavestenje-form/${this.zahtev.broj}`]);
  }

  ngOnInit(): void {
  }

}
