import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-obicna-pretraga',
  templateUrl: './obicna-pretraga.component.html',
  styleUrls: ['./obicna-pretraga.component.sass']
})
export class ObicnaPretragaComponent implements AfterViewInit {

  constructor(
    private xonomyService: XonomyService,
    private router: Router
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  get page(): string{
    return this.router.url.replace('-list', '');
  }

  pretrazi(): void{
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
    document.querySelectorAll('.xonomy').forEach(el => el.innerHTML = null);
    const pretragaXml = '<Pretraga><Fraze></Fraze><kljucne_reci></kljucne_reci></Pretraga>';
    const pretragaEditor = document.getElementById(this.page + 'obicnaPretraga');
    Xonomy.render(pretragaXml, pretragaEditor, this.xonomyService.obicnaPretraga);
  }

}
