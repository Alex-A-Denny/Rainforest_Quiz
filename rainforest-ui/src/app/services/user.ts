/**
 * User service for managing user authentication and state.
 * 
 * This service provides:
 * - User data retrieval from the backend API
 * - User registration (account creation)
 * - Current user session management using BehaviorSubject
 * - Badge awarding functionality
 * - User logout
 * 
 * The service communicates with the backend REST API at http://localhost:8080/Users
 * and handles HTTP errors gracefully by providing fallback values.
 * 
 * @author Alex Denny
 */
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, of, tap } from 'rxjs';

/**
 * Interface representing a user in the system.
 */
export interface User{
  /** The user's unique username */
  username: string;
  /** Whether the user has earned the sloth quiz badge */
  slothBadge: boolean;
  /** Whether the user has earned the parrot quiz badge */
  parrotBadge: boolean;
  /** Whether the user has earned the jaguar quiz badge */
  jagBadge: boolean;
}

/**
 * Injectable service for user operations.
 * Provides methods for user authentication, session management, and badge handling.
 */
@Injectable({
  providedIn: 'root',
})
export class UserService {
  
  private readonly BASE = 'http://localhost:8080';
  private readonly baseUrl = `${this.BASE}/Users`;

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private currentUser: User | null = null;
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  /** Observable for subscribing to current user changes */
  currentUser$ = this.currentUserSubject.asObservable();

  /**
   * Constructs the UserService.
   * @param http Angular HTTP client for making API requests
   */
  constructor(private http: HttpClient) {}

  /**
   * Fetches all users registered in the system.
   * @returns Observable of User array; returns empty array on error
   */
  getAllUsers() {
    return this.http.get<User[]>(this.baseUrl)
      .pipe(catchError(this.safe<User[]>([])));
  }

  /**
   * Creates a new user account with the specified username.
   * @param username The username for the new user
   * @returns Observable of the newly created User; returns a User with only username on error
   */
  createUser(username: string): Observable<User> {
    const newUser = {username: username}
    return this.http.post<User>(this.baseUrl, newUser, this.httpOptions)
      .pipe(catchError(this.safe<User>({ username: newUser.username ?? ''} as User)));
  }

  /**
   * Error handler that returns a fallback value on HTTP error.
   * @param fallback The value to return if an error occurs
   * @returns Function that returns an Observable of the fallback value
   */
  private safe<T>(fallback: T) {
    return (_err: unknown): Observable<T> => of(fallback);
  }

  /**
   * Sets the current logged-in user.
   * @param user The user to set as current, or null to clear
   */
  setCurrentUser(user: User | null) {
    this.currentUserSubject.next(user);
  }

  /**
   * Gets the currently logged-in user.
   * @returns The current User or null if no user is logged in
   */
  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  /**
   * Awards a badge to the current user.
   * Updates the user's badge status both locally and on the backend.
   * @param badge The badge property name to award (badgeName)
   * @returns Observable of the updated User after the backend request completes, or null if no user is logged in
   */
  awardBadge(badge: string): Observable<User | null> {
    const user = this.currentUserSubject.value;
    
    if (!user) {
      return of(null);
    }

    // Create a copy with the badge set to true
    const updatedUser = { ...user, [badge]: true };
    
    // Update backend and return the observable of the result
    return this.http.put<User>(`${this.baseUrl}/${user.username}/animals/${badge}`, updatedUser, this.httpOptions)
      .pipe(
        tap(returnedUser => {
          console.log(`Badge ${badge} awarded successfully to ${user.username}`);
          // Update the BehaviorSubject with the response from backend
          this.currentUserSubject.next(returnedUser);
        }),
        catchError((error) => {
          console.error(`Error awarding badge ${badge}:`, error);
          // Update with locally updated user on error
          this.currentUserSubject.next(updatedUser);
          return of(updatedUser);
        })
      );
  }

  /**
   * Logs out the current user by clearing the current user session.
   */
  logout() {
    this.currentUserSubject.next(null);
  }
}
