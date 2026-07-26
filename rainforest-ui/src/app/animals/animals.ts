/**
 * Animals component for displaying quiz categories.
 * 
 * After logging in, users see this component which displays three animal-themed
 * quiz categories:
 * - Jaguar Quiz
 * - Parrot Quiz
 * - Sloth Quiz
 * 
 * Users can:
 * - View which badges they've already earned
 * - Start a quiz for any animal
 * - See their badges displayed visually
 * - Logout and return to the login page
 * 
 * @author Alex Denny
 */
import { Component } from '@angular/core';
import { UserService } from '../services/user';
import { User } from '../services/user';
import { Router } from '@angular/router';

/** Type definition for supported animal quiz categories */
type AnimalKey = 'jaguar' | 'parrot' | 'sloth';

@Component({
  selector: 'app-animals',
  standalone: false,
  templateUrl: './animals.html',
  styleUrl: './animals.css',
})
export class Animals {

  /**
   * Configuration for each animal quiz category.
   * Each object contains:
   * - key: Unique identifier for the animal
   * - label: Display name for the animal
   * - badge: Corresponding badge property on the User object
   * - image: Path to the animal image
   */
  animals: { key: AnimalKey; label: string; badge: keyof User, image?: string }[] = [
    { key: 'jaguar', label: 'Jaguar', badge: 'jagBadge', image: '/app/images/JaguarElla.png'  },
    { key: 'parrot', label: 'Parrot', badge: 'parrotBadge', image: '/app/images/ParrotElla.png' },
    { key: 'sloth',  label: 'Sloth',  badge: 'slothBadge', image: '/app/images/SlothElla.png' },
  ];

  /**
   * Constructs an Animals component.
   * @param userService Service for user operations
   * @param router Angular router for navigation
   */
  constructor(private userService: UserService, private router: Router) {}

  /** Currently logged-in user */
  currentUser: User | null = null;
  
  /**
   * Angular lifecycle hook called after component initialization.
   * Loads the current user's information.
   */
  ngOnInit() {
    console.log("Animals component initialized");
    this.load(); 
  }

  /**
   * Retrieves the current user from the UserService.
   */
  load() : void{
    this.currentUser = this.userService.getCurrentUser();
  }
  
  /**
   * Navigates to the quiz page for the selected animal.
   * @param animal The animal quiz to open
   */
  openQuiz(animal: AnimalKey) {
    this.router.navigate(['/quiz', animal]);
  }

  /**
   * Awards a badge to the current user after they complete a quiz.
   * @param badge The badge to award (badgeName)
   */
  awardBadge(badge: string) {
    this.userService.awardBadge(badge).subscribe(updatedUser => {
      if (updatedUser) {
        this.currentUser = updatedUser;
      }
    });
  }

  /**
   * Logs out the current user and redirects to the login page.
   */
  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }


}
