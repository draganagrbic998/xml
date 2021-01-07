import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-odluka-list',
  templateUrl: './odluka-list.component.html',
  styleUrls: ['./odluka-list.component.sass']
})
export class OdlukaListComponent implements AfterViewInit {

  constructor(
    private odlukaService: OdlukaService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  columns: string[] = ['tipOdluke', 'datum', 'datumZahteva', 'dokumenti', 'metapodaci'];
  odluke: MatTableDataSource<OdlukaDTO> = new MatTableDataSource<OdlukaDTO>([]);
  fetchPending = true;

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiOdluke}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiOdluke}/${broj}/metadata/json`, '_blank');
  }

  ngAfterViewInit(): void {
    this.odluke.paginator = this.paginator;
    this.odlukaService.list().subscribe(
      (odluke: OdlukaDTO[]) => {
        this.odluke = new MatTableDataSource<OdlukaDTO>(odluke);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
