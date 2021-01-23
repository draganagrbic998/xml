import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { ZalbaCutanjeFormComponent } from './components/zalba/zalba-cutanje-form/zalba-cutanje-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ZalbaOdlukaFormComponent } from './components/zalba/zalba-odluka-form/zalba-odluka-form.component';
import { ResenjeFormComponent } from './components/resenje/resenje-form/resenje-form.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';
import { IzvestajListComponent } from './components/izvestaj/izvestaj-list/izvestaj-list.component';
import {
  LOGIN_PATH,
  REGISTER_PATH,
  ZALBA_CUTANJE_FORM,
  ZALBA_LIST,
  ZALBA_ODLUKA_FORM,
  RESENJE_FORM,
  RESENJE_LIST,
  IZVESTAJ_LIST,
  HTML_PATH,
  PDF_PATH,
  ODGOVOR_LIST
 } from './constants/router';
import { GradjaninGuard } from './guard/gradjanin/gradjanin.guard';
import { PoverenikGuard } from './guard/poverenik/poverenik.guard';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { OdgovorListComponent } from './components/odgovor/odgovor-list/odgovor-list.component';
import { AuthGuard } from './guard/auth/auth.guard';

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
    component: ZalbaCutanjeFormComponent,
    canActivate: [GradjaninGuard]
  },
  {
    path: ZALBA_ODLUKA_FORM,
    component: ZalbaOdlukaFormComponent,
    canActivate: [GradjaninGuard]
  },
  {
    path: ZALBA_LIST,
    component: ZalbaListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: ODGOVOR_LIST,
    component: OdgovorListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: RESENJE_FORM,
    component: ResenjeFormComponent,
    canActivate: [PoverenikGuard]
  },
  {
    path: RESENJE_LIST,
    component: ResenjeListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: IZVESTAJ_LIST,
    component: IzvestajListComponent,
    canActivate: [PoverenikGuard]
  },
  {
    path: HTML_PATH,
    component: HtmlViewerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: PDF_PATH,
    component: PdfViewerComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: LOGIN_PATH
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
