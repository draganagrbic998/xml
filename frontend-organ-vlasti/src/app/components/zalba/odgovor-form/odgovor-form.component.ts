import { AfterViewInit, Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { Odgovor } from 'src/app/models/odgovor';
import { OdgovorService } from 'src/app/services/odgovor/odgovor.service';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';

declare const Xonomy: any;

@Component({
  selector: 'app-odgovor-form',
  templateUrl: './odgovor-form.component.html',
  styleUrls: ['./odgovor-form.component.sass']
})
export class OdgovorFormComponent implements AfterViewInit {

  constructor(
    private odgovorService: OdgovorService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute
  ) { }

  savePending = false;

  save(): void{
    const odgovor: Odgovor = {detalji: Xonomy.harvest()};
    this.savePending = true;
    this.odgovorService.save(this.route.snapshot.params.brojZalbe, odgovor).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Odgovor poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const detaljiXml = '<Detalji></Detalji>';
    const detaljiEditor = document.getElementById('detaljiEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, detaljiEditor, detaljiSpecifikacija);
  }

}
