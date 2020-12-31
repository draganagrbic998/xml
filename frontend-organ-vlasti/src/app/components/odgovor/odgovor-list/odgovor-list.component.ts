import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';

@Component({
  selector: 'app-odgovor-list',
  templateUrl: './odgovor-list.component.html',
  styleUrls: ['./odgovor-list.component.sass']
})
export class OdgovorListComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdgovorService
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
