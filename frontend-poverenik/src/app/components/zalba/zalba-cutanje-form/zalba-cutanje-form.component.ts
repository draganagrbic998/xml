import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS,
  SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { ZalbaCutanje } from 'src/app/models/zalba-cutanje';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zalba-cutanje-form',
  templateUrl: './zalba-cutanje-form.component.html',
  styleUrls: ['./zalba-cutanje-form.component.sass']
})
export class ZalbaCutanjeFormComponent implements AfterViewInit {

  constructor(
    private zalbaService: ZalbaService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar
  ) { }

  savePending = false;
  zalbaForm: FormGroup = new FormGroup({
    naziv: new FormControl('Naziv organa vlasti', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    tipCutanja: new FormControl('', [Validators.required]),
    brojDokumenta: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]\d*$/)]),
    lozinka: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  get zalbaDelimicnost(): boolean{
    return this.zalbaForm.value.tipCutanja === 'nije postupio u celosti';
  }

  get prazniDetalji(): boolean{
    const parser = new DOMParser();
    const Detalji = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Detalji').item(0);
    return Detalji.textContent.trim() === '';
  }

  save(): void{
    if (this.zalbaForm.invalid){
      return;
    }
    if (this.prazniDetalji){
      this.snackBar.open(EMPTY_DETALJI, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    const zalba: ZalbaCutanje = this.zalbaForm.value;
    zalba.detalji = Xonomy.harvest();
    this.savePending = true;
    this.zalbaService.saveZalbaCutanje(zalba).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Zalba poslata!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.zalbaForm.reset();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const detaljiXml = '<Detalji></Detalji>';
    const zalbaCutanjeEditor = document.getElementById('zalbaCutanjeEditor');
    const detaljiSpecifikacija = this.xonomyService.detaljiSpecifikacija;
    Xonomy.render(detaljiXml, zalbaCutanjeEditor, detaljiSpecifikacija);
  }

}
