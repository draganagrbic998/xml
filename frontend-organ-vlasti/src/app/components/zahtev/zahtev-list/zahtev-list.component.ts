import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';
import { environment } from 'src/environments/environment';

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
  columns: string[] = ['datum', 'status', 'dokumenti', 'akcije', 'metapodaci'];
  zahtevi: MatTableDataSource<ZahtevDTO> = new MatTableDataSource<ZahtevDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${broj}/metadata/json`, '_blank');
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
  }

}
