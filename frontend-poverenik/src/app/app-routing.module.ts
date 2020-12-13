import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ZalbaDetailsComponent } from './components/zalba/zalba-details/zalba-details.component';
import { ZalbaFormComponent } from './components/zalba/zalba-form/zalba-form.component';
import { LOGIN_PATH, ZALBA_DETAILS_PATH, ZALBA_FORM_PATH } from './constants/router';

const routes: Routes = [
  {
    path: LOGIN_PATH,
    component: LoginFormComponent
  },
  {
    path: ZALBA_FORM_PATH,
    component: ZalbaFormComponent
  },
  {
    path: ZALBA_DETAILS_PATH,
    component: ZalbaDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
