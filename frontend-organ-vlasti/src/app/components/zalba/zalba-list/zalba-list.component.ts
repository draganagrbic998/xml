import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';
import { environment } from 'src/environments/environment';

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
  columns: string[] = ['tipZalbe', 'datum', 'status', 'dokumenti', 'metapodaci', 'akcije'];

  zalbe: MatTableDataSource<ZalbaDTO> = new MatTableDataSource<ZalbaDTO>([]);
  fetchPending = true;
  selectedZalba: ZalbaDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZalbe}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8081/${environment.apiZahtevi}/${broj}/metadata/json`, '_blank');
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
