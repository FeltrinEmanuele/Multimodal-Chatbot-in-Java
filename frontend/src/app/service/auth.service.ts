import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';
  private isLoggedIn: boolean = false;

  constructor(private http: HttpClient) {}

  register(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, {
      username,
      password,
    });
  }

  login(username: string, password: string): Observable<any> {
    this.isLoggedIn = true;
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password });
  }

  logout(): void {
    this.isLoggedIn = false;
  }

  isAuthenticated(): boolean {
    return this.isLoggedIn;
  }
}
