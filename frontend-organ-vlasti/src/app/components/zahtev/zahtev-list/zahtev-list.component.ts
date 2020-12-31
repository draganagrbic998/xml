import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
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

  columns: string[] = ['datum', 'status', 'html', 'pdf', 'akcije'];
  zahtevi: MatTableDataSource<ZahtevDTO> = new MatTableDataSource<ZahtevDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  @ViewChild(MatPaginator) paginator: MatPaginator;

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
