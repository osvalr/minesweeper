import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  isUserLogged: boolean

  constructor(private router: Router,
    private authService: AuthService) {
    this.authService.isLogged.subscribe(isLogged => {
      this.isUserLogged = isLogged
    })
  }

  ngOnInit() {

  }

  logout() {
    this.authService.logout()
    this.router.navigate(['/login'])
  }
}
