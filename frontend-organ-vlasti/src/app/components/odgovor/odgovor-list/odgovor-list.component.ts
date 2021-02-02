import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { OdgovorDTO } from 'src/app/models/odgovorDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { saveMetadata } from 'src/app/services/utils';

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

  metadata(broj: number, format: string): void{
    this.odgovorService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  pretraga(xml: string, tip: string): void{
    this.fetchPending = true;
    this.odgovorService.pretraga(xml, tip).subscribe(
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
    this.odgovorService.findAll().subscribe(
      (odgovori: OdgovorDTO[]) => {
        this.odgovori = new MatTableDataSource<OdgovorDTO>(odgovori);
        this.odgovori.paginator = this.paginator;
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
