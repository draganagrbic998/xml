import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-html-viewer',
  templateUrl: './html-viewer.component.html',
  styleUrls: ['./html-viewer.component.sass']
})
export class HtmlViewerComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService,
    private odlukaService: OdlukaService,
    private zalbaService: ZalbaService,
    private resenjeService: ResenjeService,
    private route: ActivatedRoute
  ) { }

  html: string;
  fetchPending =  true;

  ngOnInit(): void {
    const dokument: string = this.route.snapshot.params.dokument;
    let service;
    if (dokument === 'zahtevi'){
      service = this.zahtevService;
    }
    else if (dokument === 'odluke'){
      service = this.odlukaService;
    }
    else if (dokument === 'zalbe'){
      service = this.zalbaService;
    }
    else{
      service = this.resenjeService;
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
