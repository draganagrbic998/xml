import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-zalba-details',
  templateUrl: './zalba-details.component.html',
  styleUrls: ['./zalba-details.component.sass']
})
export class ZalbaDetailsComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  @Input() zalba: ZalbaDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  openPdf(): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${this.zalba.broj}/pdf`, '_blank');
  }

  openHtml(): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${this.zalba.broj}/html`, '_blank');
  }

  dodajResenje(): void{
    this.router.navigate([`/resenje-form/${this.zalba.broj}`]);
  }

  ngOnInit(): void {
  }

}
