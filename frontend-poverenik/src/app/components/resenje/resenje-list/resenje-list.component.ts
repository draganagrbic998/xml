import { Component, OnInit } from '@angular/core';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';

@Component({
  selector: 'app-resenje-list',
  templateUrl: './resenje-list.component.html',
  styleUrls: ['./resenje-list.component.sass']
})
export class ResenjeListComponent implements OnInit {

  constructor(
    private resenjeService: ResenjeService
  ) { }

  resenja: ResenjeDTO[];
  fetchPending = true;

  ngOnInit(): void {
    this.resenjeService.list().subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = resenja;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
