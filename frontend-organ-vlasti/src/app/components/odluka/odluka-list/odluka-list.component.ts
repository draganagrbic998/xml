import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-odluka-list',
  templateUrl: './odluka-list.component.html',
  styleUrls: ['./odluka-list.component.sass']
})
export class OdlukaListComponent implements AfterViewInit {

  constructor(
    private odlukaService: OdlukaService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['tipOdluke', 'datum', 'datumZahteva', 'dokumenti', 'metapodaci'];

  odluke: MatTableDataSource<OdlukaDTO> = new MatTableDataSource<OdlukaDTO>([]);
  fetchPending = true;
  selectedOdluka: OdlukaDTO;

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiOdluke}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiOdluke}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.odlukaService.obicnaPretraga(pretraga).subscribe(
      (odluke: OdlukaDTO[]) => {
        this.odluke = new MatTableDataSource<OdlukaDTO>(odluke);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.odlukaService.naprednaPretraga(pretraga).subscribe(
      (odluke: OdlukaDTO[]) => {
        this.odluke = new MatTableDataSource<OdlukaDTO>(odluke);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
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

    this.authService.drawerToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

}
