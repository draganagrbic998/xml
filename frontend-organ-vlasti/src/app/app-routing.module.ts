import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { RegisterFormComponent } from './components/main/register-form/register-form.component';
import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import { LOGIN_PATH, REGISTER_PATH, ZAHTEV_LIST_PATH, ZAHTEV_FORM_PATH } from './constants/router';

const routes: Routes = [
  {
    path: LOGIN_PATH,
    component: LoginFormComponent
  },
  {
    path: REGISTER_PATH,
    component: RegisterFormComponent
  },
  {
    path: ZAHTEV_FORM_PATH,
    component: ZahtevFormComponent
  },
  {
    path: ZAHTEV_LIST_PATH,
    component: ZahtevListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
