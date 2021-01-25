import { AfterViewInit, Component, EventEmitter, Input, Output } from '@angular/core';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zahtev-pretraga',
  templateUrl: './zahtev-pretraga.component.html',
  styleUrls: ['./zahtev-pretraga.component.sass']
})
export class ZahtevPretragaComponent implements AfterViewInit {

  constructor(
    private xonomyService: XonomyService
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  pretrazi(): void{
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
    const detaljiXml = '<Pretraga></Pretraga>';
    const odgovorEditor = document.getElementById('zahtevEditor');
    const detaljiSpecifikacija = this.xonomyService.zahtevPretraga;
    Xonomy.render(detaljiXml, odgovorEditor, detaljiSpecifikacija);
  }

}
