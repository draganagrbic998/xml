import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './interceptor/auth.interceptor';

import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ToolbarComponent } from './components/main/toolbar/toolbar.component';
import { FormContainerComponent } from './components/layout/form-container/form-container.component';
import { SpinnerButtonComponent } from './components/layout/spinner-button/spinner-button.component';
import { CenterContainerComponent } from './components/layout/center-container/center-container.component';
import { RegisterFormComponent } from './components/main/register-form/register-form.component';
import { ZalbaCutanjeFormComponent } from './components/zalba/zalba-cutanje-form/zalba-cutanje-form.component';
import { ZalbaOdlukaFormComponent } from './components/zalba/zalba-odluka-form/zalba-odluka-form.component';
import { ZalbaDetailsComponent } from './components/zalba/zalba-details/zalba-details.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';
import { ResenjeFormComponent } from './components/resenje/resenje-form/resenje-form.component';
import { ResenjeListComponent } from './components/resenje/resenje-list/resenje-list.component';
import { ResenjeDetailsComponent } from './components/resenje/resenje-details/resenje-details.component';
import { PreloaderComponent } from './components/layout/preloader/preloader.component';
import { EmptyContainerComponent } from './components/layout/empty-container/empty-container.component';
import { HtmlViewerComponent } from './components/main/html-viewer/html-viewer.component';
import { PdfViewerComponent } from './components/main/pdf-viewer/pdf-viewer.component';

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
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    ToolbarComponent,
    FormContainerComponent,
    SpinnerButtonComponent,
    CenterContainerComponent,
    RegisterFormComponent,
    ZalbaCutanjeFormComponent,
    ZalbaOdlukaFormComponent,
    ZalbaDetailsComponent,
    ZalbaListComponent,
    ResenjeFormComponent,
    ResenjeListComponent,
    ResenjeDetailsComponent,
    PreloaderComponent,
    EmptyContainerComponent,
    HtmlViewerComponent,
    PdfViewerComponent,
    SafeHtmlPipe
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
    MatTooltipModule,
    NgxExtendedPdfViewerModule
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
