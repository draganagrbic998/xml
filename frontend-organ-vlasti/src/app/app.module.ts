import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';

import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { ToolbarComponent } from './components/main/toolbar/toolbar.component';
import { FormContainerComponent } from './components/layout/form-container/form-container.component';
import { SpinnerButtonComponent } from './components/layout/spinner-button/spinner-button.component';
import { CenterContainerComponent } from './components/layout/center-container/center-container.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import { ObavestenjeFormComponent } from './components/odluka/obavestenje-form/obavestenje-form.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { OdlukaListComponent } from './components/odluka/odluka-list/odluka-list.component';
import { PreloaderComponent } from './components/layout/preloader/preloader.component';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { OdbijanjeFormComponent } from './components/odluka/odbijanje-form/odbijanje-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';
import { OdgovorFormComponent } from './components/odgovor/odgovor-form/odgovor-form.component';
import { IzvestajListComponent } from './components/izvestaj/izvestaj-list/izvestaj-list.component';
import { SpacerContainerComponent } from './components/layout/spacer-container/spacer-container.component';
import { OdgovorListComponent } from './components/odgovor/odgovor-list/odgovor-list.component';
import { ReferenceListComponent } from './components/main/reference-list/reference-list.component';
import { ObicnaPretragaComponent } from './components/pretraga/obicna-pretraga/obicna-pretraga.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTabsModule } from '@angular/material/tabs';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { NaprednaPretragaComponent } from './components/pretraga/napredna-pretraga/napredna-pretraga.component';
import { PretragaComponent } from './components/pretraga/pretraga.component';

@NgModule({
  declarations: [
    AppComponent,
    SafeHtmlPipe,
    ZahtevFormComponent,
    LoginFormComponent,
    ToolbarComponent,
    FormContainerComponent,
    SpinnerButtonComponent,
    CenterContainerComponent,
    RegisterFormComponent,
    ZahtevListComponent,
    ObavestenjeFormComponent,
    OdlukaListComponent,
    PreloaderComponent,
    HtmlViewerComponent,
    PdfViewerComponent,
    OdbijanjeFormComponent,
    ZalbaListComponent,
    ResenjeListComponent,
    OdgovorFormComponent,
    IzvestajListComponent,
    SpacerContainerComponent,
    OdgovorListComponent,
    ReferenceListComponent,
    ObicnaPretragaComponent,
    NaprednaPretragaComponent,
    PretragaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,

    MatToolbarModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatButtonModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatMenuModule,
    MatTableModule,
    MatPaginatorModule,
    MatSidenavModule,
    MatTooltipModule,
    MatTabsModule,
    NgxExtendedPdfViewerModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
