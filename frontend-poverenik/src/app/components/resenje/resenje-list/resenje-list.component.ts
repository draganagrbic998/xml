import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ResenjeDTO } from 'src/app/models/resenjeDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';

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
  columns: string[] = ['datum', 'status', 'html', 'pdf'];
  resenja: MatTableDataSource<ResenjeDTO> = new MatTableDataSource<ResenjeDTO>([]);
  fetchPending = true;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  ngAfterViewInit(): void {
    this.resenja.paginator = this.paginator;
    this.resenjeService.list().subscribe(
      (resenja: ResenjeDTO[]) => {
        this.resenja = new MatTableDataSource<ResenjeDTO>(resenja);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

}
