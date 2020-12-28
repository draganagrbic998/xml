import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Prijava } from 'src/app/models/prijava';
import { Registracija } from 'src/app/models/registracija';
import { OSNOVA } from 'src/app/constants/namespaces';
import { Token } from 'src/app/models/token';

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
        <aktivan>false</aktivan>
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

  login(prijava: Prijava): Observable<Token>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<Token>(`${this.API_AUTH}/login`, this.prijavaToXml(prijava), options);
  }

  register(registracija: Registracija): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_AUTH}/register`, this.registracijaToXml(registracija), options);
  }

}
