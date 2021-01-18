import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ZalbaPretraga } from 'src/app/components/zalba/zalba-pretraga/zalba-pretraga';

@Component({
  selector: 'app-zalba-pretraga',
  templateUrl: './zalba-pretraga.component.html',
  styleUrls: ['./zalba-pretraga.component.sass']
})
export class ZalbaPretragaComponent implements OnInit {

  constructor() { }

  @Input() fetchPending: boolean;
  @Output() pretragaTriggered: EventEmitter<ZalbaPretraga> = new EventEmitter();

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
