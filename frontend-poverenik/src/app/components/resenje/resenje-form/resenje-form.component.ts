import { AfterViewInit, Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ResenjeService } from 'src/app/services/resenje/resenje.service';
import { Resenje } from 'src/app/models/resenje';

declare const Xonomy: any;

@Component({
  selector: 'app-resenje-form',
  templateUrl: './resenje-form.component.html',
  styleUrls: ['./resenje-form.component.sass']
})
export class ResenjeFormComponent implements AfterViewInit {

  constructor(
    private route: ActivatedRoute,
    private xonomyService: XonomyService,
    private snackBar: MatSnackBar,
    private resenjeService: ResenjeService
  ) { }

  savePending = false;
  resenjeForm: FormGroup = new FormGroup({
    status: new FormControl('', [Validators.required]),
    datumSlanja: new FormControl('', [Validators.required]),
    datum: new FormControl('', [Validators.required]),
    odgovor: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))])
  });

  refreshForm(): void{
    if (!this.resenjeForm.value.datum){
      this.resenjeForm.get('odgovor').setErrors(null);
    }
    if (!this.resenjeForm.value.odgovor){
      this.resenjeForm.get('datum').setErrors(null);
    }
  }

  posaljiResenje(): void{
    this.refreshForm();
    if (this.resenjeForm.invalid){
      return;
    }
    const resenje: Resenje = this.resenjeForm.value;
    resenje.odluka = Xonomy.harvest();
    this.savePending = true;
    this.resenjeService.save(this.route.snapshot.params.brojZalbe, resenje).subscribe(
      () => {
        this.savePending = false;
        this.snackBar.open('Rešenje uspešno poslato!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.savePending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngAfterViewInit(): void{
    const odlukaXml = '<Odluka></Odluka>';
    const odlukaEditor = document.getElementById('odlukaEditor');
    const odlukaSpecifikacija = this.xonomyService.odlukaSpecifikacija;
    Xonomy.render(odlukaXml, odlukaEditor, odlukaSpecifikacija);
  }

}
