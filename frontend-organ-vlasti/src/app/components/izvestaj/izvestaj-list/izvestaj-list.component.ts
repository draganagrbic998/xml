import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatDrawer } from '@angular/material/sidenav';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { IzvestajDTO } from 'src/app/models/izvestajDTO';
import { AuthService } from 'src/app/services/auth/auth.service';
import { IzvestajService } from 'src/app/services/izvestaj/izvestaj.service';
import { saveMetadata } from 'src/app/services/utils';
import { IzvestajValidatorService } from './izvestaj-validator.service';

@Component({
  selector: 'app-izvestaj-list',
  templateUrl: './izvestaj-list.component.html',
  styleUrls: ['./izvestaj-list.component.sass']
})
export class IzvestajListComponent implements AfterViewInit {

  constructor(
    private izvestajService: IzvestajService,
    private izvestajValidator: IzvestajValidatorService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  columns: string[] = ['godina', 'datum', 'dokumenti', 'metapodaci'];

  izvestaji: MatTableDataSource<IzvestajDTO> = new MatTableDataSource<IzvestajDTO>([]);
  fetchPending = true;
  savePending = false;
  izvestajForm: FormGroup = new FormGroup({
    godina: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d+$/), this.izvestajValidator.godina()])
  });

  get uloga(): string{
    return this.authService.getUser()?.uloga;
  }

  metadata(broj: number, format: string): void{
    this.izvestajService.metadata(broj, format).subscribe(
      (response: string) => saveMetadata(response, format)
    );
  }

  save(): void {
    if (this.izvestajForm.invalid){
      return;
    }
    const godina: string = this.izvestajForm.controls.godina.value;
    this.savePending = true;
    this.izvestajService.add(godina).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Izveštaj kreiran i podnet!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.refreshData();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  obicnaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.izvestajService.obicnaPretraga(pretraga).subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  naprednaPretraga(pretraga: string): void{
    this.fetchPending = true;
    this.izvestajService.naprednaPretraga(pretraga).subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  refreshData(): void {
    this.izvestaji.paginator = this.paginator;
    this.izvestajService.findAll().subscribe(
      (izvestaji: IzvestajDTO[]) => {
        this.izvestaji = new MatTableDataSource<IzvestajDTO>(izvestaji);
        this.fetchPending = false;
      },
      () => {
        this.fetchPending = false;
      }
    );
  }

  ngAfterViewInit(): void {
    this.refreshData();

    this.authService.drawerToggle$.subscribe(() => {
      this.drawer.toggle();
    });
  }

}
