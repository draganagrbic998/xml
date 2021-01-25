import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-zalba-pretraga',
  templateUrl: './zalba-pretraga.component.html',
  styleUrls: ['./zalba-pretraga.component.sass']
})
export class ZalbaPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  ngOnInit(): void {
  }

}
