import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

@Component({
  selector: 'app-html-viewer',
  templateUrl: './html-viewer.component.html',
  styleUrls: ['./html-viewer.component.sass']
})
export class HtmlViewerComponent implements OnInit {

  constructor(
    private zahtevService: ZahtevService,
    private odgovorService: OdgovorService,
    private route: ActivatedRoute
  ) { }

  html: string;
  fetchPending =  true;

  ngOnInit(): void {
    const dokument = this.route.snapshot.params.dokument;
    const service = dokument === 'zahtevi' ? this.zahtevService : this.odgovorService;
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
