import { Injectable } from '@angular/core';
import { Token } from 'src/app/models/token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private readonly USER_KEY = 'user';

  saveUser(token: Token): void{
    localStorage.setItem(this.USER_KEY, JSON.stringify(token));
  }

  deleteUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

  getUser(): Token{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

}
