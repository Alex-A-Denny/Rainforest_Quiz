/**
 * Routing module for the Rainforest Quiz application.
 * 
 * Defines all routes in the application:
 * - /login: User login and registration page
 * - /:username/animals: Animal selection and quiz category menu
 * - /quiz/:animal: Quiz page for the selected animal
 * - /: Default route redirects to login
 * 
 * @author Alex Denny
 */
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './login/login';
import { Animals } from './animals/animals';
import { Quiz } from './quiz/quiz';

const routes: Routes = [
  {path: 'login', component: Login},
  {path: ':username/animals', component: Animals},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: `quiz/:animal`, component:Quiz}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
