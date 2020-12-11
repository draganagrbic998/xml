import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ZahtevDetailsComponent } from './components/zahtev/zahtev-details/zahtev-details.component';
import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import { LOGIN_PATH, ZAHTEV_DETAILS_PATH, ZAHTEV_FORM_PATH, ZAHTEV_LIST_PATH } from './constants/router';

const routes: Routes = [
  {
    path: LOGIN_PATH,
    component: LoginFormComponent
  },
  {
    path: ZAHTEV_LIST_PATH,
    component: ZahtevListComponent
  },
  {
    path: ZAHTEV_FORM_PATH,
    component: ZahtevFormComponent
  },
  {
    path: ZAHTEV_DETAILS_PATH,
    component: ZahtevDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
