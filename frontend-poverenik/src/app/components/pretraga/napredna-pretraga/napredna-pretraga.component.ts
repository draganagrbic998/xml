import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-napredna-pretraga',
  templateUrl: './napredna-pretraga.component.html',
  styleUrls: ['./napredna-pretraga.component.sass']
})
export class NaprednaPretragaComponent implements AfterViewInit {

  constructor(
    private xonomyService: XonomyService,
    private router: Router
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();
  @Input() tip: string;

  get page(): string{
    return this.router.url.replace('-list', '');
  }

  pretrazi(): void{
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
    let specifikacija;
    if (this.tip === 'zalba'){
      specifikacija = this.xonomyService.zalbaPretraga;
    }
    else if (this.tip === 'odgovor'){
      specifikacija = this.xonomyService.odgovorPretraga;
    }
    else if (this.tip === 'resenje'){
      specifikacija = this.xonomyService.resenjePretraga;
    }
    else{
      specifikacija = this.xonomyService.izvestajPretraga;
    }
    const pretragaXml = '<Pretraga></Pretraga>';
    const pretragaEditor = document.getElementById(this.page + 'naprednaPretraga');
    Xonomy.render(pretragaXml, pretragaEditor, specifikacija);
  }

}
