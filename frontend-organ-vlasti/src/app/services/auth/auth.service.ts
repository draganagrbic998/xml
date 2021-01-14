import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Profil } from 'src/app/models/profil';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  private readonly STORAGE_KEY = 'sluzbenik';

  private drawerToggle: Subject<null> = new Subject();
  drawerToggle$: Observable<null> = this.drawerToggle.asObservable();

  announceDrawerToggle(): void{
    this.drawerToggle.next();
  }

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
