import { AfterViewInit, Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { XonomyService } from 'src/app/services/xonomy/xonomy.service';
import { ZahtevService } from 'src/app/services/zahtev/zahtev.service';

declare const Xonomy: any;

@Component({
  selector: 'app-zahtev-form',
  templateUrl: './zahtev-form.component.html',
  styleUrls: ['./zahtev-form.component.sass']
})
export class ZahtevFormComponent implements AfterViewInit {

  constructor(
    private zahtevService: ZahtevService,
    private snackBar: MatSnackBar,
    private xonomyService: XonomyService
  ) { }

  zahtevPending = false;

  posaljiZahtev(): void{

    const xml = Xonomy.harvest();
    console.log(xml);
    /*

    this.zahtevPending = true;
    this.zahtevService.save(this.zahtevForm.value).subscribe(
      () => {
        this.zahtevPending = false;
        this.snackBar.open('Zahtev je uspešno poslat!', SNACKBAR_CLOSE, SNACKBAR_SUCCESS_OPTIONS);
      },
      () => {
        this.zahtevPending = false;
        this.snackBar.open(SNACKBAR_ERROR, SNACKBAR_CLOSE, SNACKBAR_ERROR_OPTIONS);
      }
    );*/
  }

  ngAfterViewInit(): void{

    const editor = document.getElementById('editor');
    const specification = this.xonomyService.testSpecification;
    const xmlString = `<Fakultet xmlns='https://github.com/draganagrbic998/xml'></Fakultet>`;
    Xonomy.render(xmlString, editor, specification);
  }

}
