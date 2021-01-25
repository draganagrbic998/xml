import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-pretraga',
  templateUrl: './pretraga.component.html',
  styleUrls: ['./pretraga.component.sass']
})
export class PretragaComponent implements OnInit {

  constructor() { }

  @Input() tip: string;
  @Input() fetchPending: boolean;
  @Output() obicnaPretragaTriggered: EventEmitter<string> = new EventEmitter();
  @Output() naprednaPretragaTriggered: EventEmitter<string> = new EventEmitter();
  tab = 0;

  ngOnInit(): void {
  }

}
