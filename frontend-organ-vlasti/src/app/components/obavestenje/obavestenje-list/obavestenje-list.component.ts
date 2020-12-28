import { Component, OnInit } from '@angular/core';
import { ObavestenjeDTO } from 'src/app/models/obavestenjeDTO';
import { ObavestenjeService } from 'src/app/services/obavestenje/obavestenje.service';

@Component({
  selector: 'app-obavestenje-list',
  templateUrl: './obavestenje-list.component.html',
  styleUrls: ['./obavestenje-list.component.sass']
})
export class ObavestenjeListComponent implements OnInit {

  constructor(
    private obavestenjeService: ObavestenjeService
  ) { }

  obavestenja: ObavestenjeDTO[];
  fetchPending = true;

  ngOnInit(): void {
    this.obavestenjeService.list().subscribe(
      (obavestenja: ObavestenjeDTO[]) => {
        this.obavestenja = obavestenja;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
