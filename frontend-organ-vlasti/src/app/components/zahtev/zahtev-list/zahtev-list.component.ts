import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { environment } from 'src/environments/environment';
import { ZahtevPretraga } from '../zahtev-pretraga/zahtev-pretraga';

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

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiZahtevi}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiZahtevi}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.zahtevService.obicnaPretraga(pretraga).subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = new MatTableDataSource<ZahtevDTO>(zahtevi);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: ZahtevPretraga): void{
    this.fetchPending = true;
    this.zahtevService.naprednaPretraga(pretraga).subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = new MatTableDataSource<ZahtevDTO>(zahtevi);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.zahtevi.paginator = this.paginator;
    this.zahtevService.list().subscribe(
      (zahtevi: ZahtevDTO[]) => {
        this.zahtevi = new MatTableDataSource<ZahtevDTO>(zahtevi);
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
