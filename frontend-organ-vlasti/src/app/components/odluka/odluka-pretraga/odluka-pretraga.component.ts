import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-odluka-pretraga',
  templateUrl: './odluka-pretraga.component.html',
  styleUrls: ['./odluka-pretraga.component.sass']
})
export class OdlukaPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  ngOnInit(): void {
  }


}
