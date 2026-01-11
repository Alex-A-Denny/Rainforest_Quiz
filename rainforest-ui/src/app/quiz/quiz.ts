import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User, UserService } from '../services/user';

type AnimalKey = 'jaguar' | 'parrot' | 'sloth';

@Component({
  selector: 'app-quiz',
  standalone: false,
  templateUrl: './quiz.html',
  styleUrl: './quiz.css',
})
export class Quiz implements OnInit {


  animals: { key: AnimalKey; label: string; badge: keyof User }[] = [
      { key: 'jaguar', label: 'Jaguar', badge: 'jagBadge' },
      { key: 'parrot', label: 'Parrot', badge: 'parrotBadge' },
      { key: 'sloth',  label: 'Sloth',  badge: 'slothBadge' },
    ];

  animal: AnimalKey | null = null;
  showQuiz: boolean = false;
  showScore: boolean = false;
  score: number = 0;
  answers: { [key: string]: string } = {};
  earnedBadge: boolean = false;


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
  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.animal = params.get('animal') as AnimalKey;
      this.resetQuiz();
      console.log('Quiz for:', this.animal);
    });
  }

  startQuiz() {
    this.showQuiz = true;
    this.showScore = false;
    this.score = 0;
    this.answers = {};
  }

  recordAnswer(question: string, value: string) {
    this.answers[question] = value;
  }

  endQuiz() {
    this.calculateScore();
    this.showScore = true;
  }

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
        this.userService.awardBadge(badge as string);
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