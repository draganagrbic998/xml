import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SNACKBAR_CLOSE, SNACKBAR_ERROR, SNACKBAR_ERROR_OPTIONS, SNACKBAR_SUCCESS_OPTIONS } from 'src/app/constants/snackbar';
import { FileService } from 'src/app/services/file/file.service';
import { ZalbaService } from 'src/app/services/zalba/zalba.service';

@Component({
  selector: 'app-zalba-form',
  templateUrl: './zalba-form.component.html',
  styleUrls: ['./zalba-form.component.sass']
})
export class ZalbaFormComponent implements OnInit {

  constructor(
    private zalbaService: ZalbaService,
    private fileService: FileService,
    private snackBar: MatSnackBar
  ) { }

  zalbaPending = false;
  zalbaForm: FormGroup = new FormGroup({
    tipZalbe: new FormControl('cutanje'),
    ime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    prezime: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    mesto: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    ulica: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    broj: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    kontakt: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    detalji: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    organVlasti: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    datumZahteva: new FormControl('', [Validators.required]),
    kopijaZahteva: new FormControl('', [Validators.required]),
    kopijaOdluke: new FormControl('', [Validators.required]),
    tipCutanja: new FormControl('nije postupio'),
    brojOdluke: new FormControl('', [Validators.required, Validators.pattern(new RegExp('\\S'))]),
    datumOdluke: new FormControl('', [Validators.required])
  });

  get zalbaCutanje(): boolean{
    return this.zalbaForm.value.tipZalbe === 'cutanje';
  }

  get zalbaDelimicnost(): boolean{
    return this.zalbaForm.value.tipZalbe === 'cutanje' && this.zalbaForm.value.tipCutanja === 'nije postupio u celosti';
  }

  get zalbaOdbijanje(): boolean{
    return this.zalbaForm.value.tipZalbe === 'odbijanje';
  }

  updateFile(key: string, file: Blob): void{
    this.fileService.getBase64(file)
    .then((result: string) => {
      this.zalbaForm.get(key).setValue(result);
    });
  }

  refreshForm(): void{
    if (this.zalbaCutanje){
      this.zalbaForm.get('brojOdluke').setErrors(null);
      this.zalbaForm.get('datumOdluke').setErrors(null);
      if (!this.zalbaDelimicnost){
        this.zalbaForm.get('kopijaOdluke').setErrors(null);
      }
    }
  }

  posaljiZalbu(): void{
    this.refreshForm();
    if (this.zalbaForm.invalid){
      return;
    }
    this.zalbaPending = true;

    this.zalbaService.save(this.zalbaForm.value).subscribe(
      () => {
        this.zalbaPending = false;
        this.snackBar.open('Žalba uspešno poslata!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.zalbaPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );
  }

  ngOnInit(): void {
  }

}
