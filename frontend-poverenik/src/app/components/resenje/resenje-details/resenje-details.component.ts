import { Component, Input, OnInit } from '@angular/core';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-resenje-details',
  templateUrl: './resenje-details.component.html',
  styleUrls: ['./resenje-details.component.sass']
})
export class ResenjeDetailsComponent implements OnInit {

  constructor() { }

  @Input() resenje: ResenjeDTO;

  getHtml(): void{
    window.open(`//localhost:8082/${environment.apiResenja}/${this.resenje.broj}/html`, '_blank');
  }

  getPdf(): void{
    window.open(`//localhost:8082/${environment.apiResenja}/${this.resenje.broj}/pdf`, '_blank');
  }

  ngOnInit(): void {
  }

}
