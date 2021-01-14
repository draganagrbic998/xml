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

import { LoginFormComponent } from './components/auth/login-form/login-form.component';
import { ToolbarComponent } from './components/main/toolbar/toolbar.component';
import { FormContainerComponent } from './components/layout/form-container/form-container.component';
import { SpinnerButtonComponent } from './components/layout/spinner-button/spinner-button.component';
import { CenterContainerComponent } from './components/layout/center-container/center-container.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { ZalbaCutanjeFormComponent } from './components/zalba/zalba-cutanje-form/zalba-cutanje-form.component';
import { ZalbaOdlukaFormComponent } from './components/zalba/zalba-odluka-form/zalba-odluka-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ResenjeFormComponent } from './components/resenje/resenje-form/resenje-form.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';
import { PreloaderComponent } from './components/layout/preloader/preloader.component';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { OdgovorListComponent } from './components/zalba/odgovor-list/odgovor-list.component';
import { IzvestajListComponent } from './components/izvestaj/izvestaj-list/izvestaj-list.component';

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
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SpacerContainerComponent } from './components/layout/spacer-container/spacer-container.component';
import { ReferenceListComponent } from './components/main/reference-list/reference-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SafeHtmlPipe,
    LoginFormComponent,
    ToolbarComponent,
    FormContainerComponent,
    SpinnerButtonComponent,
    CenterContainerComponent,
    RegisterFormComponent,
    ZalbaCutanjeFormComponent,
    ZalbaOdlukaFormComponent,
    ZalbaListComponent,
    ResenjeFormComponent,
    ResenjeListComponent,
    PreloaderComponent,
    HtmlViewerComponent,
    PdfViewerComponent,
    OdgovorListComponent,
    IzvestajListComponent,
    SpacerContainerComponent,
    ReferenceListComponent
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
    MatTooltipModule,
    MatTableModule,
    MatPaginatorModule,
    NgxExtendedPdfViewerModule,
    MatSidenavModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
