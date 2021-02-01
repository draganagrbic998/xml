import { Location } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS,
  SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Odbijanje } from 'src/app/models/odbijanje';
import { OdlukaService } from 'src/app/services/odluka/odluka.service';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-odbijanje-form',
  templateUrl: './odbijanje-form.component.html',
  styleUrls: ['./odbijanje-form.component.sass']
})
export class OdbijanjeFormComponent implements AfterViewInit {

  constructor(
    private odlukaService: OdlukaService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  savePending = false;

  get prazniDetalji(): boolean{
    const parser = new DOMParser();
    const Detalji = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Detalji').item(0);
    return Detalji.textContent.trim() === '';
  }

  save(): void{
    if (this.prazniDetalji){
      this.snackBar.open(EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    const odbijanje: Odbijanje = {detalji: Xonomy.harvest()};
    this.savePending = true;
    this.odlukaService.addOdbijanje(this.route.snapshot.params.brojZahteva, odbijanje).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Zahtev odbijen!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.location.back();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    document.querySelectorAll('.xonomy').forEach(el => el.innerHTML = null);
    const detaljiXml = '<Detalji></Detalji>';
    const odbijanjeEditor = document.getElementById('odbijanjeEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, odbijanjeEditor, detaljiSpecifikacija);
  }

}
