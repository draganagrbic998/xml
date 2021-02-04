import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { OdlukaDTO } from 'src/app/models/odlukaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { saveMetadata } from 'src/app/services/utils';

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

  metadata(broj: number, format: string): void{
    this.odlukaService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  pretraga(xml: string, tip: string): void{
    this.fetchPending = true;
    this.odlukaService.pretraga(xml, tip).subscribe(
      (odluke: OdlukaDTO[]) => {
        this.odluke = new MatTableDataSource<OdlukaDTO>(odluke);
        this.odluke.paginator = this.paginator;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.odlukaService.findAll().subscribe(
      (odluke: OdlukaDTO[]) => {
        this.odluke = new MatTableDataSource<OdlukaDTO>(odluke);
        this.odluke.paginator = this.paginator;
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
