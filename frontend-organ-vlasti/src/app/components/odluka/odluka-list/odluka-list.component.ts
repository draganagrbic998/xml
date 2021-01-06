import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';

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
  columns: string[] = ['tipOdluke', 'datum', 'datumZahteva', 'html', 'pdf'];
  odluke: MatTableDataSource<OdlukaDTO> = new MatTableDataSource<OdlukaDTO>([]);
  fetchPending = true;

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
