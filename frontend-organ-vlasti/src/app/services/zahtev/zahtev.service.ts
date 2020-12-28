import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Zahtev } from 'src/app/models/zahtev';
import { environment } from 'src/environments/environment';
import { OSNOVA, ZAHTEV } from 'src/app/constants/namespaces';
import { ZahtevDTO } from 'src/app/models/zahtevDTO';

@Injectable({
  providedIn: 'root'
})
export class ZahtevService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_ZAHTEVI = `${environment.baseUrl}/${environment.apiZahtevi}`;

  private zahtevToXml(zahtev: Zahtev): string{

    let xml = `
      ${zahtev.detalji}
      <zahtev:tipZahteva>${zahtev.tipZahteva}</zahtev:tipZahteva>
    `;
    if (zahtev.tipZahteva === 'dostava'){
      xml += '<zahtev:tipDostave>${zahtev.tipDostave}</zahtev:tipDostave>';
    }
    if (zahtev.tipDostave === 'ostalo'){
      xml += '<zahtev:opisDostave>${zahtev.opisDostave}</zahtev:opisDostave>';
    }

    return `
      <zahtev:Zahtev xmlns="${OSNOVA}"
      xmlns:zahtev="${ZAHTEV}">
        ${xml}
      </zahtev:Zahtev>
    `;

  }

  save(zahtev: Zahtev): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(this.API_ZAHTEVI, this.zahtevToXml(zahtev), options);
  }

  list(): Observable<ZahtevDTO[]>{
    return this.http.get<ZahtevDTO[]>(this.API_ZAHTEVI);
  }

}
