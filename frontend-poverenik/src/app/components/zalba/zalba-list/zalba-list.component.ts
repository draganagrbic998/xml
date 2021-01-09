import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
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
    private zalbaService: ZalbaService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  columns: string[] = ['tipZalbe', 'datum', 'status', 'dokumenti', 'metapodaci', 'odgovor', 'akcije'];
  zalbe: MatTableDataSource<ZalbaDTO> = new MatTableDataSource<ZalbaDTO>([]);
  fetchPending = true;
  sendPending = false;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  xmlMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${broj}/metadata/xml`, '_blank');
  }

  jsonMetadata(broj: string): void{
    window.open(`//localhost:8082/${environment.apiZalbe}/${broj}/metadata/json`, '_blank');
  }

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  canOdustati(zalba: ZalbaDTO): boolean{
    return zalba.status === 'cekanje' || zalba.status === 'prosledjeno' || zalba.status === 'odgovoreno';
  }

  canResiti(zalba: ZalbaDTO): boolean {
    if (zalba.status === 'odgovoreno') {
      return true;
    }

    /*
    if (zalba.status !== 'prosledjeno' && ((new Date()).getTime() - zalba.datumProsledjivanja) / 86400000 > 15) {
      return true;
    }*/

    if (zalba.status === 'prosledjeno'){
      return true;
    }

    return false;
  }

  prosledi(zalba: ZalbaDTO): void{
    this.sendPending = true;
    this.zalbaService.prosledi(zalba.broj).subscribe(
      () => {
        zalba.status = 'prosledjeno';
        this.sendPending = false;
        this.snackBar.open('Žalba prosledjena!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.sendPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  odustani(zalba: ZalbaDTO): void{
    this.sendPending = true;
    this.zalbaService.odustani(zalba.broj).subscribe(
      () => {
        zalba.status = 'odustato';
        this.sendPending = false;
        this.snackBar.open('Žalba odustata!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.sendPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  obustavi(zalba: ZalbaDTO): void{
    this.sendPending = true;
    this.zalbaService.obustavi(zalba.broj).subscribe(
      () => {
        zalba.status = 'obustavljeno';
        this.sendPending = false;
        this.snackBar.open('Žalba obustavljena!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.sendPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
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
