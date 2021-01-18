import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { OdgovorPretraga } from './odgovor-pretraga';

@Component({
  selector: 'app-odgovor-pretraga',
  templateUrl: './odgovor-pretraga.component.html',
  styleUrls: ['./odgovor-pretraga.component.sass']
})
export class OdgovorPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<OdgovorPretraga> = new EventEmitter();
  pretragaForma: FormGroup = new FormGroup({
    operacija: new FormControl('and'),
    datum: new FormControl(''),
    izdatoU: new FormControl(''),
    organVlasti: new FormControl('')
  });

  ngOnInit(): void {
  }
}
