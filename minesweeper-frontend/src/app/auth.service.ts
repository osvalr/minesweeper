import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private httpClient: HttpClient) { }

  login(user: string, password: string): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(environment.address + "/login", { 'user': user, 'password': password });
  }

  logout(): Observable<any> {
    localStorage.removeItem('SESSION_TOKEN')
    return this.httpClient.get(environment.address + "/logout");
  }

  isUserLogged(): boolean {
    return localStorage.getItem('SESSION_TOKEN') !== undefined
  }

}
