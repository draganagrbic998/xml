import { Injectable } from '@angular/core';
import { Profil } from 'src/app/models/profil';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private readonly STORAGE_KEY = 'poverenik';

  saveUser(profil: Profil): void{
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(profil));
  }

  deleteUser(): void{
    localStorage.removeItem(this.STORAGE_KEY);
  }

  getUser(): Profil{
    return JSON.parse(localStorage.getItem(this.STORAGE_KEY));
  }

}
