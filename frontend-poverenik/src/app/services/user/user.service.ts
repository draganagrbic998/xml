import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from 'src/app/models/login';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient
  ) { }

  private readonly API_AUTH = `${environment.baseUrl}/${environment.apiAuth}`;

  private jsonToXml(login: Login): string{
    return `
      <login>
        <email>${login.email}</email>
        <password>${login.password}</password>
      </login>
    `;
  }

  login(login: Login): Observable<string>{
    const options = { headers: new HttpHeaders().set('Content-Type', 'text/xml') };
    return this.http.post<{token: string}>(`${this.API_AUTH}/login`, this.jsonToXml(login), options).pipe(
      map((response: {token: string}) => response.token)
    );
  }

}
