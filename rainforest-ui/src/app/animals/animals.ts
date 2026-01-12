import { Component } from '@angular/core';
import { UserService } from '../services/user';
import { User } from '../services/user';
import { Router } from '@angular/router';



type AnimalKey = 'jaguar' | 'parrot' | 'sloth';


@Component({
  selector: 'app-animals',
  standalone: false,
  templateUrl: './animals.html',
  styleUrl: './animals.css',
})
export class Animals {


  animals: { key: AnimalKey; label: string; badge: keyof User }[] = [
    { key: 'jaguar', label: 'Jaguar', badge: 'jagBadge' },
    { key: 'parrot', label: 'Parrot', badge: 'parrotBadge' },
    { key: 'sloth',  label: 'Sloth',  badge: 'slothBadge' },
  ];

  constructor(private userService: UserService, private router: Router) {}


  currentUser: User | null = null;
  
  ngOnInit() {
    console.log("Animals component initialized");
    this.load(); 
  }

  load() : void{
    this.currentUser = this.userService.getCurrentUser();
  }
  
  openQuiz(animal: AnimalKey) {
    this.router.navigate(['/quiz', animal]);
  }


  awardBadge(badge: string) {
    this.currentUser = this.userService.awardBadge(badge);
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }


}
