import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-odgovor-list',
  templateUrl: './odgovor-list.component.html',
  styleUrls: ['./odgovor-list.component.sass']
})
export class OdgovorListComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdgovorService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['broj', 'datum', 'datumZalbe', 'dokumenti', 'metapodaci'];

  odgovori: MatTableDataSource<OdgovorDTO> = new MatTableDataSource<OdgovorDTO>([]);
  fetchPending = true;
  selectedOdgovor: OdgovorDTO;

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiOdgovori}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiOdgovori}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.odgovorService.obicnaPretraga(pretraga).subscribe(
      (odgovori: OdgovorDTO[]) => {
        this.odgovori = new MatTableDataSource<OdgovorDTO>(odgovori);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.odgovorService.naprednaPretraga(pretraga).subscribe(
      (odgovori: OdgovorDTO[]) => {
        this.odgovori = new MatTableDataSource<OdgovorDTO>(odgovori);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
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

    this.authService.drawerToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

}
