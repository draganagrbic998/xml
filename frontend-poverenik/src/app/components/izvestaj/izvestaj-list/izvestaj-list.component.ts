import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { IzvestajDTO } from 'src/app/models/izvestajDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { IzvestajService } from 'src/app/services/izvestaj/izvestaj.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-izvestaj-list',
  templateUrl: './izvestaj-list.component.html',
  styleUrls: ['./izvestaj-list.component.sass']
})
export class IzvestajListComponent implements AfterViewInit {

  constructor(
    private izvestajService: IzvestajService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['godina', 'datum', 'dokumenti', 'metapodaci'];

  izvestaji: MatTableDataSource<IzvestajDTO> = new MatTableDataSource<IzvestajDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiIzvestaji}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiIzvestaji}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.izvestajService.obicnaPretraga(pretraga).subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.izvestajService.naprednaPretraga(pretraga).subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.izvestaji.paginator = this.paginator;
    this.izvestajService.list().subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
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
