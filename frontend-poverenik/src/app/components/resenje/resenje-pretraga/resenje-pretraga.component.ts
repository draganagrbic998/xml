import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ResenjePretraga } from './resenje-pretraga';

@Component({
  selector: 'app-resenje-pretraga',
  templateUrl: './resenje-pretraga.component.html',
  styleUrls: ['./resenje-pretraga.component.sass']
})
export class ResenjePretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending;
  @Output() pretragaTriggered: EventEmitter<ResenjePretraga> = new EventEmitter();
  pretragaForma: FormGroup = new FormGroup({
    operacija: new FormControl('and'),
    datum: new FormControl(''),
    izdatoU: new FormControl(''),
    organVlasti: new FormControl(''),
    tipZalbe: new FormControl(''),
    status: new FormControl('')
  });

  ngOnInit(): void {
  }

}
