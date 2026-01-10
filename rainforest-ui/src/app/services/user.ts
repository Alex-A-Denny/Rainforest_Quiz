import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, of } from 'rxjs';


export interface User{
  username: string;
  slothBadge: boolean;
  parrotBadge: boolean;
  jagBadge: boolean;
}


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
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAllUsers() {
    return this.http.get<User[]>(this.baseUrl)
      .pipe(catchError(this.safe<User[]>([])));
  }

  createUser(username: string): Observable<User> {
    const newUser = {username: username}
    return this.http.post<User>(this.baseUrl, newUser, this.httpOptions)
      .pipe(catchError(this.safe<User>({ username: newUser.username ?? ''} as User)));
  }

  private safe<T>(fallback: T) {
    return (_err: unknown): Observable<T> => of(fallback);
  }


  //get/set the current logged-in user
  setCurrentUser(user: User | null) {
    this.currentUserSubject.next(user);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  awardBadge(badge: string): User | null {
    const user = this.currentUserSubject.value;
    
    if (!user) {
      return null;
    }

    //update badge locally
    const updatedUser = { ...user, [badge]: true };
    
    //update backend
    this.http.put<User>(`${this.baseUrl}/${user.username}/animals/${badge}`, updatedUser, this.httpOptions)
      .pipe(catchError(this.safe<User>(updatedUser)))
      .subscribe(returnedUser => {
        //update the BehaviorSubject with the response from backend
        this.currentUserSubject.next(returnedUser);
      });
    
    
    return updatedUser;
  }

}
