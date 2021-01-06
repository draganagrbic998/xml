import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-zalba-list',
  templateUrl: './zalba-list.component.html',
  styleUrls: ['./zalba-list.component.sass']
})
export class ZalbaListComponent implements AfterViewInit {

  constructor(
    private authService: AuthService,
    private zalbaService: ZalbaService
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  columns: string[] = ['tipZalbe', 'datum', 'html', 'pdf', 'akcije'];
  zalbe: MatTableDataSource<ZalbaDTO> = new MatTableDataSource<ZalbaDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  ngAfterViewInit(): void {
    this.zalbe.paginator = this.paginator;
    this.zalbaService.list().subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = new MatTableDataSource<ZalbaDTO>(zalbe);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
