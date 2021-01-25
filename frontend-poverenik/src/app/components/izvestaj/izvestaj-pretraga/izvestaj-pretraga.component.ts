import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-izvestaj-pretraga',
  templateUrl: './izvestaj-pretraga.component.html',
  styleUrls: ['./izvestaj-pretraga.component.sass']
})
export class IzvestajPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<string> = new EventEmitter();

  ngOnInit(): void {
  }

}
