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
  /** Array of all users fetched from the backend */
  allUsers: User[] = []

  /** Currently logged-in or registered user */
  user?: User;

  /** Username input by the user */
  username = '';

  /**
   * Constructs a Login component.
   * @param userService Service for user operations and backend communication
   * @param router Angular router for navigation
   */
  constructor(private userService: UserService, private router : Router) {}

  /**
   * Angular lifecycle hook called after component initialization.
   * Loads all existing users from the backend.
   */
  ngOnInit() {
    this.load();
  }

  /**
   * Fetches all users from the backend and stores them in the component.
   */
  load() : void{
    this.userService.getAllUsers().subscribe(allUsers => {(this.allUsers = allUsers)
      console.log(this.allUsers.length);
      allUsers.forEach(u => console.log(u.username))
      });
  }

  /**
   * Authenticates a user or creates a new user account.
   * 
   * If the entered username exists, logs the user in and stores their data.
   * If the username doesn't exist, creates a new user account.
   * After successful login/registration, navigates to the animals page.
   */
  login() : void{
    console.log("Logging in as: " + this.username);
    let foundUser = false;

    this.allUsers.forEach((user) =>{
      if(user.username === this.username){
        console.log("User found: " + user.username);
        this.user = user; //local cache
        foundUser = true;

        //store user in userService
        this.userService.setCurrentUser(user);
        //move to the animal page
        this.router.navigate([`${user.username}/animals`]);
      }
    })
    if(!foundUser){
      //register/create new user
      console.log("User not found, creating new user: " + this.username);
      this.userService.createUser(this.username).subscribe(
        newUser => {
          console.log("New user created: " + newUser.username);
          this.user = newUser; //local cache
          this.userService.setCurrentUser(newUser);
          this.router.navigate([`${newUser.username}/animals`]);
        }
      );
    }
  }


}