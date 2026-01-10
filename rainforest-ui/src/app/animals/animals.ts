import { Component } from '@angular/core';
import { UserService } from '../services/user';
import { User } from '../services/user';


@Component({
  selector: 'app-animals',
  standalone: false,
  templateUrl: './animals.html',
  styleUrl: './animals.css',
})
export class Animals {

  constructor(private userService: UserService) {}


  currentUser: User | null = null;
  
  ngOnInit() {
    console.log("Animals component initialized");
    this.load(); 
  }

  load() : void{
    this.currentUser = this.userService.getCurrentUser();
  }
  
  awardBadge(badge: string) {
    this.currentUser = this.userService.awardBadge(badge);
  }


}
