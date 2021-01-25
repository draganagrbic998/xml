import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-odgovor-pretraga',
  templateUrl: './odgovor-pretraga.component.html',
  styleUrls: ['./odgovor-pretraga.component.sass']
})
export class OdgovorPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  ngOnInit(): void {
  }
}
