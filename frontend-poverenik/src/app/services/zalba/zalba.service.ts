import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OSNOVA, POVERENIK, XSI } from 'src/app/constants/namespaces';
import { Zalba } from 'src/app/models/zalba';
import { formatDate } from 'src/app/utils/utils';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {

  constructor(
    private http: HttpClient
  ) { }

  private jsonToXml(zalba: Zalba): string{

    let base =
    `
      <osnova:Osoba>
        <osnova:ime>${zalba.ime}</osnova:ime>
        <osnova:prezime>${zalba.prezime}</osnova:prezime>
      </osnova:Osoba>
      <osnova:Adresa>
        <osnova:mesto>${zalba.mesto}</osnova:mesto>
        <osnova:ulica>${zalba.ulica}</osnova:ulica>
        <osnova:broj>${zalba.broj}</osnova:broj>
      </osnova:Adresa>
      <osnova:detalji>${zalba.detalji}</osnova:detalji>
      <osnova:datum></osnova:datum>
      <osnova:kontakt>${zalba.kontakt}</osnova:kontakt>
      <osnova:potpis></osnova:potpis>
      <osnova:odgovoreno></osnova:odgovoreno>
      <poverenik:organVlasti>${zalba.organVlasti}</poverenik:organVlasti>
      <poverenik:datumZahteva>${formatDate(zalba.datumZahteva)}</poverenik:datumZahteva>
      <poverenik:kopijaZahteva>${zalba.kopijaZahteva}</poverenik:kopijaZahteva>
    `;
    if (zalba.kopijaOdluke){
      base += `<poverenik:kopijaOdluke>${zalba.kopijaOdluke}</poverenik:kopijaOdluke>`;
    }
    if (zalba.tipZalbe === 'cutanje'){
      return `
        <poverenik:Zalba
        xmlns:osnova="${OSNOVA}"
        xmlns:poverenik="${POVERENIK}"
        xmlns:xsi="${XSI}"
        xsi:type="poverenik:TZalbaCutanje">
          ${base}
          <poverenik:tipCutanja>${zalba.tipCutanja.replace(' ', '_')}</poverenik:tipCutanja>
        </poverenik:Zalba>
      `;
    }
    else{
      return `
        <poverenik:Zalba
        xmlns:osnova="${OSNOVA}"
        xmlns:poverenik="${POVERENIK}"
        xmlns:xsi="${XSI}"
        xsi:type="poverenik:TZalbaOdbijanje">
          ${base}
          <poverenik:brojOdluke>${zalba.brojOdluke}</poverenik:brojOdluke>
          <poverenik:datumOdluke>${formatDate(zalba.datumOdluke)}</poverenik:datumOdluke>
        </poverenik:Zalba>
    `;

    }
  }

  save(zalba: Zalba): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${environment.baseUrl}/${environment.apiZalbe}`, this.jsonToXml(zalba), options);
  }

}
