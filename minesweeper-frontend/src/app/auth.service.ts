import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isLoggedSubject: BehaviorSubject<boolean>;
  public isLogged: Observable<boolean>;
  constructor(private httpClient: HttpClient) {
    this.isLoggedSubject = new BehaviorSubject<boolean>(this.getToken() !== null);
    this.isLogged = this.isLoggedSubject.asObservable();
  }
  getToken(): string {
    return localStorage.getItem('SESSION_TOKEN')
  }
  public get getCurrentUserValue(): boolean {
    return this.isLoggedSubject.value;
  }


  login(user: string, password: string): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(environment.address + "/login", { 'user': user, 'password': password })
      .pipe(
        map(login => {
          this.isLoggedSubject.next(login.token !== null);
          return login
        })
      )
  }

  logout() {
    this.isLoggedSubject.next(false)
    localStorage.removeItem('SESSION_TOKEN')
  }

  isUserLogged(): boolean {
    return this.getToken() !== null
  }

}
