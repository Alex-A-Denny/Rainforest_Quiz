/**
 * Quiz component for displaying and grading quiz questions.
 * 
 * This component displays a multiple-choice quiz about a selected rainforest animal.
 * Users answer 5 questions and receive a score. If they score 80% or higher,
 * they earn the corresponding badge.
 * 
 * Flow:
 * 1. User selects start quiz
 * 2. Questions are displayed one at a time or all at once
 * 3. User submits their answers
 * 4. Score is calculated and displayed
 * 5. If score >= 80%, badge is awarded to the user
 * 
 * @author Alex Denny
 */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from '../services/user';

/** Type definition for supported animal quiz categories */
type AnimalKey = 'jaguar' | 'parrot' | 'sloth';

@Component({
  selector: 'app-quiz',
  standalone: false,
  templateUrl: './quiz.html',
  styleUrl: './quiz.css',
})
export class Quiz implements OnInit {

  /**
   * Configuration for each animal quiz category.
   */
  animals: { key: AnimalKey; label: string; badge: keyof User }[] = [
      { key: 'jaguar', label: 'Jaguar', badge: 'jagBadge' },
      { key: 'parrot', label: 'Parrot', badge: 'parrotBadge' },
      { key: 'sloth',  label: 'Sloth',  badge: 'slothBadge' },
    ];

  /** The current animal quiz being taken */
  animal: AnimalKey | null = null;
  
  /** Whether the quiz questions are currently displayed */
  showQuiz: boolean = false;
  
  /** Whether the score results are currently displayed */
  showScore: boolean = false;
  
  /** The user's current score (0-5) */
  score: number = 0;
  
  /** User's answers mapped by question identifier */
  answers: { [key: string]: string } = {};
  
  /** Whether the user earned the badge for this quiz */
  earnedBadge: boolean = false;

  /**
   * Correct answers for each animal's quiz.
   * Maps animal -> question -> correct answer letter
   */
  private correctAnswers: { [key in AnimalKey]: { [key: string]: string } } = {
    jaguar: {
      q1: 'C',
      q2: 'C',
      q3: 'B',
      q4: 'C',
      q5: 'A'
    },
    parrot: {
      q1: 'C',
      q2: 'B',
      q3: 'C',
      q4: 'A',
      q5: 'B'
    },
    sloth: {
      q1: 'C',
      q2: 'C',
      q3: 'D',
      q4: 'B',
      q5: 'B'
    }
  };
  
  /**
   * Constructs a Quiz component.
   * @param route Angular activated route for accessing route parameters
   * @param router Angular router for navigation
   * @param userService Service for user operations
   */
  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {}

  /**
   * Angular lifecycle hook called after component initialization.
   * Extracts the animal from the route parameter and initializes the quiz.
   */
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.animal = params.get('animal') as AnimalKey;
      this.resetQuiz();
      console.log('Quiz for:', this.animal);
    });
  }

  /**
   * Starts the quiz by displaying questions and resetting the score.
   */
  startQuiz() {
    this.showQuiz = true;
    this.showScore = false;
    this.score = 0;
    this.answers = {};
  }

  /**
   * Records the user's answer for a specific question.
   * @param question The question identifier
   * @param value The answer letter selected
   */
  recordAnswer(question: string, value: string) {
    this.answers[question] = value;
  }

  /**
   * Ends the quiz by calculating the score and displaying results.
   */
  endQuiz() {
    this.calculateScore();
    this.showScore = true;
  }

  /**
   * Calculates the quiz score by comparing answers to correct answers.
   * If score >= 80%, awards the badge to the user.
   */
  calculateScore() {
    if (!this.animal) {
      console.error('No animal selected for the quiz.');  
      return;
    }
    
    this.score = 0;
    const correctAnswersForAnimal = this.correctAnswers[this.animal];
    
    for (let i = 1; i <= 5; i++) {
      const questionKey = `q${i}`;
      if (this.answers[questionKey] === correctAnswersForAnimal[questionKey]) {
        this.score++;
      }
    }

    this.earnedBadge = this.score >= 4;

    if (this.earnedBadge) {
      // Award badge logic here
      console.log(`Badge earned for ${this.animal}!`);
      const badge = this.animals.find(a => a.key === this.animal)?.badge;
      if (badge) {
        this.userService.awardBadge(badge as string).subscribe(
          (updatedUser) => {
            if (updatedUser) {
              console.log(`Badge ${badge} successfully awarded to ${updatedUser.username}`);
            }
          },
          (error) => {
            console.error(`Failed to award badge ${badge}:`, error);
          }
        );
      }
    }
  }

  resetQuiz() {
    this.showQuiz = false;
    this.showScore = false;
    this.score = 0;
    this.answers = {};
    this.earnedBadge = false;
  }


  goBack() {
    this.router.navigate([`/${this.userService.getCurrentUser()?.username}/animals`]);
  }
}