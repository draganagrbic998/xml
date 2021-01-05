import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';

@Component({
  selector: 'app-odluka-list',
  templateUrl: './odluka-list.component.html',
  styleUrls: ['./odluka-list.component.sass']
})
export class OdlukaListComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdlukaService
  ) { }

  columns: string[] = ['tipOdgovora', 'datum', 'datumZahteva', 'html', 'pdf'];
  odgovori: MatTableDataSource<OdgovorDTO> = new MatTableDataSource<OdgovorDTO>([]);
  fetchPending = true;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit(): void {
    this.odgovori.paginator = this.paginator;

    this.odgovorService.list().subscribe(
      (odgovori: OdgovorDTO[]) => {
        this.odgovori = new MatTableDataSource<OdgovorDTO>(odgovori);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
