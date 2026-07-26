/**
 * Root module for the Rainforest Quiz Angular application.
 * 
 * AppModule is the main module that bootstraps the application. It declares
 * all components used in the application and imports necessary Angular modules
 * for routing, HTTP communication, and form handling.
 * 
 * Components declared:
 * - AppComponent: Root component
 * - Login: Login/user registration page
 * - Animals: Animal selection and quiz menu page
 * - Quiz: Quiz questions and scoring page
 * 
 * @author Alex Denny
 */
import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app';
import { Login } from './login/login';
import { Animals } from './animals/animals';
import { Quiz } from './quiz/quiz';

@NgModule({
  declarations: [
    AppComponent,
    Login,
    Animals,
    Quiz
  ],
  imports: [
    BrowserModule,
    CommonModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
