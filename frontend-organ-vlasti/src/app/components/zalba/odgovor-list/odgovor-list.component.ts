import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-odgovor-list',
  templateUrl: './odgovor-list.component.html',
  styleUrls: ['./odgovor-list.component.sass']
})
export class OdgovorListComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdgovorService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  columns: string[] = ['broj', 'datum', 'datumZalbe', 'dokumenti', 'metapodaci'];
  odgovori: MatTableDataSource<OdgovorDTO> = new MatTableDataSource<OdgovorDTO>([]);
  fetchPending = true;
  selectedOdgovor: OdgovorDTO;

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiOdgovori}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiOdgovori}/${broj}/metadata/json`, '_blank');
  }

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
