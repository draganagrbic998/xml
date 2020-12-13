import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { FormContainerComponent } from './components/layout/form-container/form-container.component';
import { SpinnerButtonComponent } from './components/layout/spinner-button/spinner-button.component';
import { LoginFormComponent } from './components/main/login-form/login-form.component';
import { ToolbarComponent } from './components/main/toolbar/toolbar.component';
import { ZalbaDetailsComponent } from './components/zalba/zalba-details/zalba-details.component';
import { ZalbaFormComponent } from './components/zalba/zalba-form/zalba-form.component';
import { ZalbaListComponent } from './components/zalba/zalba-list/zalba-list.component';

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
import { SpacerContainerComponent } from './components/layout/spacer-container/spacer-container.component';

@NgModule({
  declarations: [
    AppComponent,
    FormContainerComponent,
    SpinnerButtonComponent,
    LoginFormComponent,
    ToolbarComponent,
    ZalbaDetailsComponent,
    ZalbaFormComponent,
    ZalbaListComponent,
    SpacerContainerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
