import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { ZalbaPretraga } from 'src/app/components/zalba/zalba-pretraga/zalba-pretraga';
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
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['tipZalbe', 'datum', 'status', 'dokumenti', 'metapodaci', 'akcije'];

  zalbe: MatTableDataSource<ZalbaDTO> = new MatTableDataSource<ZalbaDTO>([]);
  fetchPending = true;
  sendPending = false;
  selectedZalba: ZalbaDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  convertDate(date: string): string{
    const array: string[] = date.split('-');
    return `${array[2]}.${array[1]}.${array[0]}.`;
  }

  xmlMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiZalbe}/${broj}/metadata_xml`;
  }

  jsonMetadata(broj: string): string{
    return `${environment.baseUrl}/${environment.apiZalbe}/${broj}/metadata_json`;
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.zalbaService.obicnaPretraga(pretraga).subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = new MatTableDataSource<ZalbaDTO>(zalbe);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: ZalbaPretraga): void{
    this.fetchPending = true;
    this.zalbaService.naprednaPretraga(pretraga).subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = new MatTableDataSource<ZalbaDTO>(zalbe);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  canOdustati(zalba: ZalbaDTO): boolean{
    return zalba.status === 'cekanje' || zalba.status === 'prosledjeno' || zalba.status === 'odgovoreno';
  }

  canObustaviti(zalba: ZalbaDTO): boolean{
    console.log(zalba.status);
    return zalba.status === 'odustato' || zalba.status === 'obavesteno';
  }

  canResiti(zalba: ZalbaDTO): boolean {
    if (zalba.status === 'odgovoreno') {
      return true;
    }

    /*
    if (zalba.status !== 'prosledjeno' && ((new Date()).getTime() - zalba.datumProsledjivanja) / 86400000 > 15) {
      return true;
    }
    */

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
        zalba.status = 'ponisteno';
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

    this.authService.drawerToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

}
