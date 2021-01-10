import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
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
  columns: string[] = ['godina', 'datum', 'dokumenti', 'metapodaci'];
  izvestaji: MatTableDataSource<IzvestajDTO> = new MatTableDataSource<IzvestajDTO>([]);
  fetchPending = true;

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiIzvestaji}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiIzvestaji}/${broj}/metadata/json`, '_blank');
  }

  get uloga(): string{
    return this.authService.getUser()?.uloga;
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
  }

}