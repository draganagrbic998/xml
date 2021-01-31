import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IzvestajService } from 'src/app/services/izvestaj/izvestaj.service';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-pdf-viewer',
  templateUrl: './pdf-viewer.component.html',
  styleUrls: ['./pdf-viewer.component.sass']
})
export class PdfViewerComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService,
    private odlukaService: OdlukaService,
    private zalbaService: ZalbaService,
    private odgovorService: OdgovorService,
    private resenjeService: ResenjeService,
    private izvestajService: IzvestajService,
    private route: ActivatedRoute
  ) { }

  pdf: ArrayBuffer;

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
    else if (dokument === 'odgovori'){
      service = this.odgovorService;
    }
    else if (dokument === 'resenja'){
      service = this.resenjeService;
    }
    else{
      service = this.izvestajService;
    }
    service.find(this.route.snapshot.params.broj, 'arraybuffer').subscribe(
      (pdf: ArrayBuffer) => {
        this.pdf = pdf;
      }
    );

  }

}
