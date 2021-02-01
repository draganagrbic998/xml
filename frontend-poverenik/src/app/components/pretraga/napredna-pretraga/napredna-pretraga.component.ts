import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_PRETRAGA_ERROR_OPTIONS } from 'src/app/constants/snackbar';
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
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();
  @Input() tip: string;

  get page(): string{
    return this.router.url.replace('-list', '');
  }

  pretrazi(): void{
    const xml = Xonomy.harvest();
    const parser = new DOMParser();
    const pretraga = parser.parseFromString(xml, 'text/xml').getElementsByTagName('Pretraga').item(0);
    if (pretraga.lastChild === null){
      this.snackBar.open('Morate uneti bar jedan metapodatak!', SNACKBAR_CLOSE, SNACKBAR_PRETRAGA_ERROR_OPTIONS);
      return;
    }
    if (pretraga.lastChild.nodeName === 'and' || pretraga.lastChild.nodeName === 'or'){
      this.snackBar.open('Logička operacija se ne sme nalaziti na kraju!', SNACKBAR_CLOSE, SNACKBAR_PRETRAGA_ERROR_OPTIONS);
      return;
    }
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
    document.querySelectorAll('.xonomy').forEach(el => el.innerHTML = null);
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
