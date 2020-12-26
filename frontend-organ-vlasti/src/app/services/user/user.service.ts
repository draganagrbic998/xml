import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Login } from 'src/app/models/login';
import { Registration } from 'src/app/models/registration';
import { map } from 'rxjs/operators';
import { OSNOVA_NAMESPACE } from 'src/app/constants/namespaces';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_AUTH = `${environment.baseUrl}/${environment.apiAuth}`;

  private loginToXml(login: Login): string{
    return `
      <login>
        <email>${login.email}</email>
        <password>${login.password}</password>
      </login>
    `;
  }

  private registrationToXml(registration: Registration): string{
    return `
      <Korisnik xmlns="${OSNOVA_NAMESPACE}">
        <email>${registration.email}</email>
        <lozinka>${registration.lozinka}</lozinka>
        <Gradjanin>
            <Osoba>
                <ime>${registration.ime}</ime>
                <prezime>${registration.prezime}</prezime>
            </Osoba>
            <Adresa>
                <mesto>${registration.mesto}</mesto>
                <ulica>${registration.ulica}</ulica>
                <broj>${registration.broj}</broj>
            </Adresa>
        </Gradjanin>
    </Korisnik>
    `;
  }

  login(login: Login): Observable<string>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<{token: string}>(`${this.API_AUTH}/login`, this.loginToXml(login), options).pipe(
      map((response: {token: string}) => response.token)
    );
  }

  register(registration: Registration): Observable<null>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<null>(`${this.API_AUTH}/register`, this.registrationToXml(registration), options);
  }

}
