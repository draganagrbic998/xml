import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Referenca } from 'src/app/models/referenca';

@Component({
  selector: 'app-reference-list',
  templateUrl: './reference-list.component.html',
  styleUrls: ['./reference-list.component.sass']
})
export class ReferenceListComponent implements OnInit {

  constructor() { }

  @Input() reference: Referenca[];
  referenceList: MatTableDataSource<Referenca>;
  columns: string[] = ['tip', 'dokumenti'];

  ngOnInit(): void {
    this.referenceList = new MatTableDataSource(this.reference);
  }

}
