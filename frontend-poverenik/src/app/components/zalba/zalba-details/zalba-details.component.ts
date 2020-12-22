import { Component, OnInit } from '@angular/core';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-zalba-details',
  templateUrl: './zalba-details.component.html',
  styleUrls: ['./zalba-details.component.sass']
})
export class ZalbaDetailsComponent implements OnInit {

  constructor(
    private zalbaService: ZalbaService
  ) { }

  zalbaView = '';

  click(): void{
    this.zalbaService.view(1).subscribe(
      res => {
        console.log(res);
        this.zalbaView = res;
      }
    );
  }

  ngOnInit(): void{

  }

}
