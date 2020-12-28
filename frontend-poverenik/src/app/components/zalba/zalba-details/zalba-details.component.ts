import { Component, Input, OnInit } from '@angular/core';
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
    private authService: AuthService
  ) { }

  @Input() zalba: ZalbaDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  getHtml(): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${this.zalba.broj}/html`, '_blank');
  }

  getPdf(): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${this.zalba.broj}/pdf`, '_blank');
  }

  ngOnInit(): void {
  }

}
