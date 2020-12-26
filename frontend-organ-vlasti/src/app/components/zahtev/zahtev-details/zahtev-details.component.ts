import { Component, Input, OnInit } from '@angular/core';
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
    private zahtevService: ZahtevService
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

  openPdf(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${broj}/pdf`, '_blank');
  }

  openHtml(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${broj}/html`, '_blank');
  }

  ngOnInit(): void {
  }

}
