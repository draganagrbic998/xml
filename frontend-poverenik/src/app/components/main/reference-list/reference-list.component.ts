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

  columns: string[] = ['tip', 'dokumenti'];
  @Input() reference: Referenca[];
  referenceList: MatTableDataSource<Referenca>;

  ngOnInit(): void {
    this.referenceList = new MatTableDataSource(this.reference);
  }

}
