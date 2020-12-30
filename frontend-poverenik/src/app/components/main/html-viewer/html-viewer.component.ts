import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    private route: ActivatedRoute
  ) { }

  html: string;
  fetchPending =  true;

  ngOnInit(): void {
    const dokument = this.route.snapshot.params.dokument;
    const service = dokument === 'zalbe' ? this.zalbaService : this.resenjeService;
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
