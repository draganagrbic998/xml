import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { Resenje } from 'src/app/models/resenje';
import { Location } from '@angular/common';

declare const Xonomy: any;

@Component({
  selector: 'app-resenje-form',
  templateUrl: './resenje-form.component.html',
  styleUrls: ['./resenje-form.component.sass']
})
export class ResenjeFormComponent implements AfterViewInit {

  constructor(
    private resenjeService: ResenjeService,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  savePending = false;
  resenjeForm: FormGroup = new FormGroup({
    status: new FormControl('', [Validators.required])
  });

  get praznaDispozitiva(): boolean{
    const parser = new DOMParser();
    const Dispozitiva = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Dispozitiva').item(0);
    return Dispozitiva.textContent.trim() === '';
  }

  get praznoObrazlozenje(): boolean{
    const parser = new DOMParser();
    const Obrazlozenje = parser.parseFromString(Xonomy.harvest(), 'text/xml').getElementsByTagName('Obrazlozenje').item(0);
    return Obrazlozenje.textContent.trim() === '';
  }

  save(): void{
    if (this.resenjeForm.invalid){
      return;
    }
    if (this.praznaDispozitiva && this.resenjeForm.value.status === 'odobreno'){
      this.snackBar.open('Morate uneti dispozitivu!', SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    if (this.praznoObrazlozenje){
      this.snackBar.open('Morate uneti obrazlozenje!', SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      return;
    }
    const resenje: Resenje = this.resenjeForm.value;
    resenje.odluka = Xonomy.harvest();
    this.savePending = true;
    this.resenjeService.add(this.route.snapshot.params.brojZalbe, resenje).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Rešenje poslato!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
        this.location.back();
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const odlukaXml = '<Odluka><Dispozitiva></Dispozitiva><Obrazlozenje></Obrazlozenje></Odluka>';
    const resenjeEditor = document.getElementById('resenjeEditor');
    const odlukaSpecifikacija = this.xonomyService.odlukaSpecifikacija;
    Xonomy.render(odlukaXml, resenjeEditor, odlukaSpecifikacija);
  }

}
