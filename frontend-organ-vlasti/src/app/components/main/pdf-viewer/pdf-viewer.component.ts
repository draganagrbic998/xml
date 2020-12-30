import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-pdf-viewer',
  templateUrl: './pdf-viewer.component.html',
  styleUrls: ['./pdf-viewer.component.sass']
})
export class PdfViewerComponent implements OnInit {

  constructor(
    private route: ActivatedRoute
  ) { }

  get path(): string{
    return `//localhost:8081/api/${this.route.snapshot.params.dokument}/${this.route.snapshot.params.broj}/pdf`;
  }

  ngOnInit(): void {
  }

}
