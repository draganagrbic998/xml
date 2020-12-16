import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
      <organVlasti>${zalba.organVlasti}</organVlasti>
      <datumZahteva>${formatDate(zalba.datumZahteva)}</datumZahteva>
      <detalji>${zalba.detalji}</detalji>
      <kopijaZahteva>${zalba.kopijaZahteva}</kopijaZahteva>
    `;
    if (zalba.kopijaOdluke){
      base += `<kopijaOdluke>${zalba.kopijaOdluke}</kopijaOdluke>`;
    }
    if (zalba.tipZalbe === 'cutanje'){
      return `
        <zalba>
          ${base}
          <tipCutanja>${zalba.tipCutanja.replace(' ', '_')}</tipCutanja>
        </zalba>
      `;
    }
    else{
      return `
        <zalba>
          ${base}
          <brojOdluke>${zalba.brojOdluke}</brojOdluke>
          <datumOdluke>${formatDate(zalba.datumOdluke)}</datumOdluke>
        </zalba>
      `;

    }
  }

  save(zalba: Zalba): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${environment.baseUrl}/${environment.apiZalbe}`, this.jsonToXml(zalba), options);
  }

}
