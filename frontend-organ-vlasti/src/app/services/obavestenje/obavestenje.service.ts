import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DOKUMENT_NAMESPACE, OSNOVA_NAMESPACE } from 'src/app/constants/namespaces';
import { Obavestenje } from 'src/app/models/obavestenje';
import { ObavestenjeDTO } from 'src/app/models/obavestenjeDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ObavestenjeService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_OBAVESTENJA = `${environment.baseUrl}/${environment.apiObavestenja}`;

  private obavestenjeToXml(obavestenje: Obavestenje): string{

    return `
      <dokument:Obavestenje xmlns="${OSNOVA_NAMESPACE}"
      xmlns:dokument="${DOKUMENT_NAMESPACE}">
        <dokument:Zahtev>
          ${obavestenje.detalji}
        </dokument:Zahtev>
        <dokument:Uvid>
          <Adresa>
              <mesto>${obavestenje.mesto}</mesto>
              <ulica>${obavestenje.ulica}</ulica>
              <broj>${obavestenje.broj}</broj>
          </Adresa>
          <dokument:kancelarija>${obavestenje.kancelarija}</dokument:kancelarija>
          <dokument:datum>2020-12-12</dokument:datum>
          <dokument:pocetak>${obavestenje.pocetak}</dokument:pocetak>
          <dokument:kraj>${obavestenje.kraj}</dokument:kraj>
        </dokument:Uvid>
        <dokument:kopija>${obavestenje.kopija}</dokument:kopija>
      </dokument:Obavestenje>
    `;

  }

  save(brojZahteva: string, obavestenje: Obavestenje): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_OBAVESTENJA}/${brojZahteva}`, this.obavestenjeToXml(obavestenje), options);
  }

  list(): Observable<ObavestenjeDTO[]>{
    return this.http.get<ObavestenjeDTO[]>(this.API_OBAVESTENJA);
  }

}
