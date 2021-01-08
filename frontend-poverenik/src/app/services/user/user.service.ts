import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Prijava } from 'src/app/models/prijava';
import { Registracija } from 'src/app/models/registracija';
import { OSNOVA } from 'src/app/constants/namespaces';
import { Profil } from 'src/app/models/profil';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_AUTH = `${environment.baseUrl}/${environment.apiAuth}`;

  private prijavaToXml(prijava: Prijava): string{
    return `
      <prijava>
        <mejl>${prijava.mejl}</mejl>
        <lozinka>${prijava.lozinka}</lozinka>
      </prijava>
    `;
  }

  private registracijaToXml(registracija: Registracija): string{
    return `
      <Korisnik xmlns="${OSNOVA}">
        <lozinka>${registracija.lozinka}</lozinka>
        <Osoba>
          <mejl>${registracija.mejl}</mejl>
          <ime>${registracija.ime}</ime>
          <prezime>${registracija.prezime}</prezime>
        </Osoba>
        <Adresa>
            <mesto>${registracija.mesto}</mesto>
            <ulica>${registracija.ulica}</ulica>
            <broj>${registracija.broj}</broj>
        </Adresa>
      </Korisnik>
    `;
  }

  private xmlToProfil(xml: string): Profil{
    const parser = new DOMParser();
    const document = parser.parseFromString(xml, 'text/xml');
    return {
      token: document.getElementsByTagName('token')[0].textContent,
      uloga: document.getElementsByTagName('uloga')[0].textContent,
      mejl: document.getElementsByTagName('mejl')[0].textContent,
      ime: document.getElementsByTagName('ime')[0].textContent,
      prezime: document.getElementsByTagName('prezime')[0].textContent
    };
  }

  login(prijava: Prijava): Observable<Profil>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml'), responseType: 'text' as 'json' };
    return this.http.post<string>(`${this.API_AUTH}/login`, this.prijavaToXml(prijava), options).pipe(
      map((xml: string) => this.xmlToProfil(xml))
    );
  }

  register(registracija: Registracija): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_AUTH}/register`, this.registracijaToXml(registracija), options);
  }

}
