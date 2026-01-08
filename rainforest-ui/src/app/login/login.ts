import { Component } from '@angular/core';
import { UserService, User } from '../services/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true
})
export class Login {

  allUsers: User[] = []

  user?: User;

  username = '';

  constructor(private userService: UserService, private router : Router) {}

  ngOnInit() {
    this.load();
  }

  load() : void{
    this.userService.getAllUsers().subscribe(allUsers => {(this.allUsers = allUsers)
      console.log(this.allUsers.length);
      allUsers.forEach(u => console.log(u.username))
      });
  }

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
        //TODO : add proper routing
        this.router.navigate(['/animals']);
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