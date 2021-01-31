import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatTableDataSource } from '@angular/material/table';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-resenje-list',
  templateUrl: './resenje-list.component.html',
  styleUrls: ['./resenje-list.component.sass']
})
export class ResenjeListComponent implements AfterViewInit {

  constructor(
    private resenjeService: ResenjeService,
    private authService: AuthService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['datum', 'status', 'dokumenti', 'metapodaci'];

  resenja: MatTableDataSource<ResenjeDTO> = new MatTableDataSource<ResenjeDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiResenja}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiResenja}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.resenjeService.obicnaPretraga(pretraga).subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.resenjeService.naprednaPretraga(pretraga).subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.resenja.paginator = this.paginator;
    this.resenjeService.findAll().subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
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
