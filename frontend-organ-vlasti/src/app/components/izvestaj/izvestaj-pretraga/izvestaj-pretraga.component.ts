import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { IzvestajPretraga } from './izvestaj-pretraga';

@Component({
  selector: 'app-izvestaj-pretraga',
  templateUrl: './izvestaj-pretraga.component.html',
  styleUrls: ['./izvestaj-pretraga.component.sass']
})
export class IzvestajPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<IzvestajPretraga> = new EventEmitter();

  pretragaForma: FormGroup = new FormGroup({
    operacija: new FormControl('and'),
    godina: new FormControl(''),
    datum: new FormControl(''),
    izdatoU: new FormControl(''),
    organVlasti: new FormControl('')
  });

  ngOnInit(): void {
  }

}
