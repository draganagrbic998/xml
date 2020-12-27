import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { RegisterFormComponent } from './components/main/register-form/register-form.component';
import { ZalbaCutanjeFormComponent } from './components/zalba/zalba-cutanje-form/zalba-cutanje-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ZalbaOdlukaFormComponent } from './components/zalba/zalba-odluka-form/zalba-odluka-form.component';
import { LOGIN_PATH, REGISTER_PATH, ZALBA_CUTANJE_FORM, ZALBA_LIST, ZALBA_ODLUKA_FORM } from './constants/router';

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
    path: ZALBA_CUTANJE_FORM,
    component: ZalbaCutanjeFormComponent
  },
  {
    path: ZALBA_ODLUKA_FORM,
    component: ZalbaOdlukaFormComponent
  },
  {
    path: ZALBA_LIST,
    component: ZalbaListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
