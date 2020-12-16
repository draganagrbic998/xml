import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
    if (zahtev.tipUvida !== 'dostava'){
      return `
        <zahtev>
          <detalji>${zahtev.detalji}</detalji>
          <tipUvida>${zahtev.tipUvida}</tipUvida>
        </zahtev>
      `;
    }
    return `
      <zahtev>
        <detalji>${zahtev.detalji}</detalji>
        <tipDostave>${zahtev.tipDostave}</tipDostave>
        <opisDostave>${zahtev.opisDostave}</opisDostave>
      </zahtev>
    `;
  }

  save(zahtev: Zahtev): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${environment.baseUrl}/${environment.apiZahtevi}`, this.jsonToXml(zahtev), options);
  }

}
