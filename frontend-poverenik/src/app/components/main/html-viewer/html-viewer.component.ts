import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IzvestajService } from 'src/app/services/izvestaj/izvestaj.service';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-html-viewer',
  templateUrl: './html-viewer.component.html',
  styleUrls: ['./html-viewer.component.sass']
})
export class HtmlViewerComponent implements OnInit {

  constructor(
    private zalbaService: ZalbaService,
    private resenjeService: ResenjeService,
    private izvestajService: IzvestajService,
    private odgovorService: OdgovorService,
    private route: ActivatedRoute
  ) { }

  html = `
    <div style="height: 100%; width: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center;">
      <h1>GREŠKA PRILIKOM UČITAVANJA DOKUMENTA!!</h1>
    </div>
  `;
  fetchPending =  true;

  ngOnInit(): void {
    const dokument = this.route.snapshot.params.dokument;
    let service;
    if (dokument === 'zalbe'){
      service = this.zalbaService;
    }
    else if (dokument === 'resenja'){
      service = this.resenjeService;
    }
    else if (dokument === 'izvestaji'){
      service = this.izvestajService;
    }
    else{
      service = this.odgovorService;
    }
    service.view(this.route.snapshot.params.broj).subscribe(
      (html: string) => {
        this.html = html;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
