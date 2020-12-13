import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ORGAN_VLASTI, OSNOVA, XSI } from 'src/app/constants/namespaces';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient
  ) { }

  private jsonToXml(zahtev: Zahtev): string{
    const base =
    `
      <osnova:Osoba>
        <osnova:ime>${zahtev.ime}</osnova:ime>
        <osnova:prezime>${zahtev.prezime}</osnova:prezime>
      </osnova:Osoba>
      <osnova:Adresa>
        <osnova:mesto>${zahtev.mesto}</osnova:mesto>
        <osnova:ulica>${zahtev.ulica}</osnova:ulica>
        <osnova:broj>${zahtev.broj}</osnova:broj>
      </osnova:Adresa>
      <osnova:detalji>${zahtev.detalji}</osnova:detalji>
      <osnova:datum></osnova:datum>
      <osnova:kontakt>${zahtev.kontakt}</osnova:kontakt>
      <osnova:potpis></osnova:potpis>
      <osnova:odgovoreno></osnova:odgovoreno>
      <organ_vlasti:OrganVlasti>
        <organ_vlasti:naziv></organ_vlasti:naziv>
        <organ_vlasti:sediste></organ_vlasti:sediste>
      </organ_vlasti:OrganVlasti>
    `;
    if (zahtev.tipUvida !== 'dostava'){
      return `
        <organ_vlasti:Zahtev
        xmlns:osnova="${OSNOVA}"
        xmlns:organ_vlasti="${ORGAN_VLASTI}"
        xmlns:xsi="${XSI}"
        xsi:type="organ_vlasti:TZahtevUvid">
          ${base}
          <organ_vlasti:tipUvida>${zahtev.tipUvida}</organ_vlasti:tipUvida>
        </organ_vlasti:Zahtev>
      `;
    }
    else{
      return `
        <organ_vlasti:Zahtev
        xmlns:osnova="${OSNOVA}"
        xmlns:organ_vlasti="${ORGAN_VLASTI}"
        xmlns:xsi="${XSI}"
        xsi:type="organ_vlasti:TZahtevDostava">
          ${base}
          <organ_vlasti:tipDostave>${zahtev.tipDostave}</organ_vlasti:tipDostave>
          <organ_vlasti:opisDostave>${zahtev.opisDostave}</organ_vlasti:opisDostave>
        </organ_vlasti:Zahtev>
      `;

    }
  }

  save(zahtev: Zahtev): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${environment.baseUrl}/${environment.apiZahtevi}`, this.jsonToXml(zahtev), options);
  }

}
