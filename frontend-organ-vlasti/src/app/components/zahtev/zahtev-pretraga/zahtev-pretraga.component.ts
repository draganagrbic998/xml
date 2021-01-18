import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ZahtevPretraga } from './zahtev-pretraga';

@Component({
  selector: 'app-zahtev-pretraga',
  templateUrl: './zahtev-pretraga.component.html',
  styleUrls: ['./zahtev-pretraga.component.sass']
})
export class ZahtevPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<ZahtevPretraga> = new EventEmitter();
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
