import { AuthService } from './auth.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
    constructor(private authService: AuthService) { }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (this.authService.isUserLogged()) {
            req.headers.set('Authorization', localStorage.getItem('SESSION_TOKEN'))
        }
        return next.handle(req);
    }

}