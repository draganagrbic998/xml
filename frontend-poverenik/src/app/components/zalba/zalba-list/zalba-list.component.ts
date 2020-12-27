import { Component, OnInit } from '@angular/core';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-zalba-list',
  templateUrl: './zalba-list.component.html',
  styleUrls: ['./zalba-list.component.sass']
})
export class ZalbaListComponent implements OnInit {

  constructor(
    private zalbaService: ZalbaService
  ) { }

  zalbe: ZalbaDTO[];

  ngOnInit(): void {
    this.zalbaService.list().subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = zalbe;
      }
    );
  }

}
