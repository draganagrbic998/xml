import { Component, OnInit } from '@angular/core';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

@Component({
  selector: 'app-zahtev-list',
  templateUrl: './zahtev-list.component.html',
  styleUrls: ['./zahtev-list.component.sass']
})
export class ZahtevListComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService
  ) { }

  zahtevi: ZahtevDTO[];
  fetchPending = true;

  ngOnInit(): void {
    this.zahtevService.list().subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = zahtevi;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
