import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZalbaDTO } from 'src/app/models/zalbaDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { saveMetadata } from 'src/app/services/utils';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

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
  @ViewChild(MatSort) sort: MatSort;
  columns: string[] = ['tipZalbe', 'datum', 'status', 'dokumenti', 'metapodaci', 'akcije'];

  zalbe: MatTableDataSource<ZalbaDTO> = new MatTableDataSource<ZalbaDTO>([]);
  fetchPending = true;
  sendPending = false;
  selectedZalba: ZalbaDTO;

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  metadata(broj: number, format: string): void{
    this.zalbaService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  canOdustati(zalba: ZalbaDTO): boolean{
    return zalba.status === 'cekanje' || zalba.status === 'prosledjeno' || zalba.status === 'odgovoreno';
  }

  canObustaviti(zalba: ZalbaDTO): boolean{
    return zalba.status === 'odustato' || zalba.status === 'obavesteno';
  }

  canResiti(zalba: ZalbaDTO): boolean {
    if (zalba.status === 'odgovoreno') {
      return true;
    }

    /*
    if (zalba.status === 'prosledjeno' && ((new Date()).getTime() - zalba.datumProsledjivanja) / 86400000 > 15) {
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

  pretraga(xml: string, tip: string): void{
    this.fetchPending = true;
    this.zalbaService.pretraga(xml, tip).subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = new MatTableDataSource<ZalbaDTO>(zalbe);
        this.zalbe.paginator = this.paginator;
        this.zalbe.sort = this.sort;
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.zalbaService.findAll().subscribe(
      (zalbe: ZalbaDTO[]) => {
        this.zalbe = new MatTableDataSource<ZalbaDTO>(zalbe);
        this.zalbe.paginator = this.paginator;
        this.zalbe.sort = this.sort;
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
