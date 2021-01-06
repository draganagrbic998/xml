import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { ObavestenjeFormComponent } from './components/odluka/obavestenje-form/obavestenje-form.component';
import { OdlukaListComponent } from './components/odluka/odluka-list/odluka-list.component';
import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import {
  LOGIN_PATH,
  REGISTER_PATH,
  ZAHTEV_LIST_PATH,
  ZAHTEV_FORM_PATH,
  OBAVESTENJE_FORM,
  ODLUKA_LIST_PATH,
  HTML_PATH,
  PDF_PATH,
  ODBIJANJE_FORM,
  ODGOVOR_PATH,
  ZALBA_LIST_PATH,
  RESENJE_LIST_PATH
} from './constants/router';
import { GradjaninGuard } from './guard/gradjanin/gradjanin.guard';
import { SluzbenikGuard } from './guard/sluzbenik/sluzbenik.guard';
import { OdbijanjeFormComponent } from './components/odluka/odbijanje-form/odbijanje-form.component';
import { OdgovorFormComponent } from './components/zalba/odgovor-form/odgovor-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';

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
    component: ZahtevFormComponent,
    canActivate: [GradjaninGuard]
  },
  {
    path: ZAHTEV_LIST_PATH,
    component: ZahtevListComponent
  },
  {
    path: OBAVESTENJE_FORM,
    component: ObavestenjeFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODBIJANJE_FORM,
    component: OdbijanjeFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: ODLUKA_LIST_PATH,
    component: OdlukaListComponent
  },
  {
    path: ZALBA_LIST_PATH,
    component: ZalbaListComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: RESENJE_LIST_PATH,
    component: ResenjeListComponent,
      canActivate: [SluzbenikGuard]
  },
  {
    path: ODGOVOR_PATH,
    component: OdgovorFormComponent,
    canActivate: [SluzbenikGuard]
  },
  {
    path: HTML_PATH,
    component: HtmlViewerComponent
  },
  {
    path: PDF_PATH,
    component: PdfViewerComponent
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
