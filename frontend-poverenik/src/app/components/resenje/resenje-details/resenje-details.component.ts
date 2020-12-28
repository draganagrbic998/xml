import { Component, Input, OnInit } from '@angular/core';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-resenje-details',
  templateUrl: './resenje-details.component.html',
  styleUrls: ['./resenje-details.component.sass']
})
export class ResenjeDetailsComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  @Input() resenje: ResenjeDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  openPdf(): void{
    window.open(`//localhost:8082/${environment.apiResenja}/${this.resenje.broj}/pdf`, '_blank');
  }

  openHtml(): void{
    window.open(`//localhost:8082/${environment.apiResenja}/${this.resenje.broj}/html`, '_blank');
  }

  ngOnInit(): void {
  }

}
