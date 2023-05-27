import { links } from 'src/app/utils/link-constants';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, map, Observable, throwError } from 'rxjs';
import { AuthenticationService } from '../services/authentication-service/authentication.service';
import { httpStatusCodes } from '../utils/http-status-codes';

/* This class is an interceptor that will add a token to the header of every request that is sent to
the server. */
@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {}

  /**
   * If the user is logged in, add the JWT to the request header and send the request. If the user is not
   * logged in, send the request without the JWT.
   * @param request - HttpRequest<any> - The request object that is being intercepted.
   * @param {HttpHandler} next - HttpHandler - The next interceptor in the chain.
   * @returns The next.handle(request) is being returned.
   */
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authenticationService.getToken();
    if (token) {
      const newRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });

      return next.handle(newRequest).pipe(
        map((event: HttpEvent<any>) => {
          return event;
        }),
        catchError((error: any) => this.handleError(error))
      );
    }

    return next.handle(request);
  }

  /**
   * If the error is unauthorized, logout the user. Otherwise, return an error.
   * @param {HttpErrorResponse} error - HttpErrorResponse - The error response object.
   * @returns Observable<never>
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    switch (error.status) {
      case httpStatusCodes.unauthorized.code:
        this.authenticationService.logout();
        break;
      case httpStatusCodes.internalServerError.code:
        this.authenticationService.logout();
        break;
      default:
        return throwError(() => new Error(error.message));
    }

    return throwError(() => new Error(error.message));
  }
}
