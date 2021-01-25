import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-resenje-pretraga',
  templateUrl: './resenje-pretraga.component.html',
  styleUrls: ['./resenje-pretraga.component.sass']
})
export class ResenjePretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  ngOnInit(): void {
  }

}
