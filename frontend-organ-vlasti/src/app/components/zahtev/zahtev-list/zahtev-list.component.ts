import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { saveMetadata } from 'src/app/services/utils';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

@Component({
  selector: 'app-zahtev-list',
  templateUrl: './zahtev-list.component.html',
  styleUrls: ['./zahtev-list.component.sass']
})
export class ZahtevListComponent implements AfterViewInit {

  constructor(
    private zahtevService: ZahtevService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['tip', 'datum', 'status', 'dokumenti', 'metapodaci', 'akcije'];

  zahtevi: MatTableDataSource<ZahtevDTO> = new MatTableDataSource<ZahtevDTO>([]);
  fetchPending = true;
  selectedZahtev: ZahtevDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  metadata(broj: number, format: string): void{
    this.zahtevService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  pretraga(xml: string, tip: string): void{
    this.fetchPending = true;
    this.zahtevService.pretraga(xml, tip).subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = new MatTableDataSource<ZahtevDTO>(zahtevi);
        this.zahtevi.paginator = this.paginator;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.zahtevService.findAll().subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = new MatTableDataSource<ZahtevDTO>(zahtevi);
        this.zahtevi.paginator = this.paginator;
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
