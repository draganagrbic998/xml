import { Component, OnInit } from '@angular/core';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

@Component({
  selector: 'app-zahtev-details',
  templateUrl: './zahtev-details.component.html',
  styleUrls: ['./zahtev-details.component.sass']
})
export class ZahtevDetailsComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService
  ) { }

  zahtevVieW = '';

  click(): void{
    this.zahtevService.view(1).subscribe(
      res => {
        console.log(res);
        this.zahtevVieW = res;
      }
    );
  }

  ngOnInit(): void {
  }

}
