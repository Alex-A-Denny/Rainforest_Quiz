/**
 * Root component for the Rainforest Quiz application.
 * 
 * AppComponent is the top-level component that serves as the container
 * for the entire application. It uses Angular's router outlet to display
 * different page components based on the current route.
 * 
 * @author Alex Denny
 */
import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class AppComponent {
  /** Application title displayed in the browser tab and header */
  protected readonly title = signal('Rainforest Quiz');
}
