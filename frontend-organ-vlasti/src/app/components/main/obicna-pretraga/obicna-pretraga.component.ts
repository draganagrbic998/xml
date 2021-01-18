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
  ) { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  triggerPretraga(): void{
    this.pretragaTriggered.emit(Xonomy.harvest());
  }

  ngAfterViewInit(): void{
  }

}
