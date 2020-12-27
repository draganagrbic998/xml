import { Component, Input, OnInit } from '@angular/core';
import { ObavestenjeDTO } from 'src/app/models/obavestenjeDTO';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-obavestenje-details',
  templateUrl: './obavestenje-details.component.html',
  styleUrls: ['./obavestenje-details.component.sass']
})
export class ObavestenjeDetailsComponent implements OnInit {

  constructor() { }

  @Input() obavestenje: ObavestenjeDTO;


  openPdf(): void{
    window.open(`//localhost:8081/${environment.apiObavestenja}/${this.obavestenje.broj}/pdf`, '_blank');
  }

  openHtml(): void{
    window.open(`//localhost:8081/${environment.apiObavestenja}/${this.obavestenje.broj}/html`, '_blank');
  }

  ngOnInit(): void {
  }

}
