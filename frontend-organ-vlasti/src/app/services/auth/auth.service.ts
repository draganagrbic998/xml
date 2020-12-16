import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private readonly USER_KEY = 'user';

  saveUser(token: string): void{
    localStorage.setItem(this.USER_KEY, token);
  }

  deleteUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

  getUser(): string{
    return localStorage.getItem(this.USER_KEY);
  }

}
