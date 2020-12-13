import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ZalbaDetailsComponent } from './components/zalba/zalba-details/zalba-details.component';
import { ZalbaFormComponent } from './components/zalba/zalba-form/zalba-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { LOGIN_PATH, ZALBA_DETAILS_PATH, ZALBA_FORM_PATH, ZALBA_LIST_PATH } from './constants/router';

const routes: Routes = [
  {
    path: LOGIN_PATH,
    component: LoginFormComponent
  },
  {
    path: ZALBA_LIST_PATH,
    component: ZalbaListComponent
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
