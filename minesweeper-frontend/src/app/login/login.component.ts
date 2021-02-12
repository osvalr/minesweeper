import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { AuthService } from './../auth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher, MatSnackBar } from '@angular/material';
import { of } from 'rxjs';
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  passwordFormControl = new FormControl('', [
    Validators.required,
  ]);

  usernameFormControl = new FormControl('', [
    Validators.required,
  ]);

  matcher = new MyErrorStateMatcher();
  constructor(private authService: AuthService,
    private snackBar: MatSnackBar,
    private router: Router) { }

  ngOnInit() {
    if (this.authService.isUserLogged()) {
      this.router.navigate(['/game'])
    }
  }

  login() {
    this.authService.login(this.usernameFormControl.value, this.passwordFormControl.value)
      .pipe(
        catchError(this.handleError.bind(this))
      )
      .subscribe(res => {
        localStorage.setItem("SESSION_TOKEN", res.token);
        this.router.navigateByUrl('/game')
      })
  }
  handleError(): any {
    this.snackBar.open('Invalid user login, please try again', '', {
      duration: 500,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
    })
    return of()
  }
}