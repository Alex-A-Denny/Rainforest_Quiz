/**
 * Bootstrap file for the Rainforest Quiz Angular application.
 * 
 * This is the main entry point for the application. It bootstraps the root
 * AppModule into the browser's DOM using Angular's platform browser API.
 * 
 * The ngZoneEventCoalescing option optimizes change detection by batching
 * asynchronous events together.
 */

import { platformBrowser } from '@angular/platform-browser';
import { AppModule } from './app/app-module';

platformBrowser().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
})
  .catch(err => console.error(err));
