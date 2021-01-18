import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { OdlukaPretraga } from './odluka-pretraga';

@Component({
  selector: 'app-odluka-pretraga',
  templateUrl: './odluka-pretraga.component.html',
  styleUrls: ['./odluka-pretraga.component.sass']
})
export class OdlukaPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<OdlukaPretraga> = new EventEmitter();
  pretragaForma: FormGroup = new FormGroup({
    operacija: new FormControl('and'),
    datum: new FormControl(''),
    mesto: new FormControl(''),
    tip: new FormControl(''),
    status: new FormControl(''),
    izdatoU: new FormControl(''),
    organVlasti: new FormControl('')
  });

  ngOnInit(): void {
  }


}
