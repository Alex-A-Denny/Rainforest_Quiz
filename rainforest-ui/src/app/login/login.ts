/**
 * Login component for user authentication and registration.
 * 
 * This component allows users to:
 * - View all existing users
 * - Enter a username to log in
 * - Create a new user account if the username doesn't exist
 * - Navigate to the animals page after successful login/registration
 * 
 * The component communicates with the backend UserService to fetch existing
 * users and register new users.
 * 
 * @author Alex Denny
 */
import { Component } from '@angular/core';
import { UserService, User } from '../services/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: false
})
export class Login {
  /** Currently logged-in or registered user */
  user?: User;

  /** Username and password input by the user */
  username = '';
  password = '';
  loginError = '';

  /**
   * Constructs a Login component.
   * @param userService Service for user operations and backend communication
   * @param router Angular router for navigation
   */
  constructor(private userService: UserService, private router : Router) {}

  /**
   * Authenticates an existing user through the backend session endpoint.
   */
  login() : void{
    this.loginError = '';
    this.userService.login(this.username, this.password).subscribe({
      next: user => {
        this.user = user;
        this.router.navigate([`${user.username}/animals`]);
      },
      error: () => {
        this.loginError = 'Invalid username or password.';
      }
    });
  }

  /** Registers a new user through the backend. */
  register() : void{
    this.loginError = '';
    this.userService.createUser(this.username, this.password).subscribe(newUser => {
      this.user = newUser;
      this.userService.setCurrentUser(newUser);
      this.router.navigate([`${newUser.username}/animals`]);
    });
  }


}