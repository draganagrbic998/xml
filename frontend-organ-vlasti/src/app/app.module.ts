import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './interceptor/auth.interceptor';

import { ZahtevFormComponent } from './components/zahtev/zahtev-form/zahtev-form.component';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ToolbarComponent } from './components/main/toolbar/toolbar.component';
import { FormContainerComponent } from './components/layout/form-container/form-container.component';
import { ZahtevDetailsComponent } from './components/zahtev/zahtev-details/zahtev-details.component';
import { SpinnerButtonComponent } from './components/layout/spinner-button/spinner-button.component';
import { PaginatorComponent } from './components/layout/paginator/paginator.component';
import { CenterContainerComponent } from './components/layout/center-container/center-container.component';
import { ZahtevListComponent } from './components/zahtev/zahtev-list/zahtev-list.component';
import { ObavestenjeFormComponent } from './components/obavestenje/obavestenje-form/obavestenje-form.component';
import { RegisterFormComponent } from './components/main/register-form/register-form.component';

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

import { SafeHtmlPipe } from './safe-html.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ZahtevFormComponent,
    LoginFormComponent,
    ToolbarComponent,
    FormContainerComponent,
    ZahtevDetailsComponent,
    SpinnerButtonComponent,
    PaginatorComponent,
    CenterContainerComponent,
    RegisterFormComponent,
    SafeHtmlPipe,
    ZahtevListComponent,
    ObavestenjeFormComponent
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
    MatNativeDateModule
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
