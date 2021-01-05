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
import { ObavestenjeFormComponent } from './components/odgovor/obavestenje-form/obavestenje-form.component';
import { RegisterFormComponent } from './components/auth/register-form/register-form.component';
import { OdgovorListComponent } from './components/odgovor/odgovor-list/odgovor-list.component';
import { PreloaderComponent } from './components/layout/preloader/preloader.component';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';
import { OdbijanjeFormComponent } from './components/odgovor/odbijanje-form/odbijanje-form.component';

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
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';

@NgModule({
  declarations: [
    AppComponent,
    ZahtevFormComponent,
    LoginFormComponent,
    ToolbarComponent,
    FormContainerComponent,
    SpinnerButtonComponent,
    CenterContainerComponent,
    RegisterFormComponent,
    ZahtevListComponent,
    ObavestenjeFormComponent,
    OdgovorListComponent,
    PreloaderComponent,
    SafeHtmlPipe,
    HtmlViewerComponent,
    PdfViewerComponent,
    OdbijanjeFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

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
