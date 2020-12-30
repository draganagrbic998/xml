import { Injectable } from '@angular/core';
import { Profil } from 'src/app/models/profil';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private readonly USER_KEY = 'user';

  saveUser(profil: Profil): void{
    localStorage.setItem(this.USER_KEY, JSON.stringify(profil));
  }

  deleteUser(): void{
    localStorage.removeItem(this.USER_KEY);
  }

  getUser(): Profil{
    return JSON.parse(localStorage.getItem(this.USER_KEY));
  }

}
