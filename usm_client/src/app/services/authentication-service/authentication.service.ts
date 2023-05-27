import { links } from 'src/app/utils/link-constants';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationApiServiceService } from '../api-services/authentication-service/authentication-api-service.service';
import { TokenStorageService } from '../storage/token-storage.service';
import { throwError, catchError, Observable, map } from 'rxjs';
import { TokenResponse } from '../../models/token-respone.model';

/* A service class that is used to authenticate the user. */
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(
    private authenticationApi: AuthenticationApiServiceService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  /**
   * It takes a username and password, calls the authenticationApi.authenticate function, catches any
   * errors, and then maps the token to the handleToken function.
   * @param {string} username - string, password: string
   * @param {string} password - string
   * @returns Observable<TokenResponse>;
   */
  login(username: string, password: string): Observable<TokenResponse> {
    return this.authenticationApi
      .authenticate(username, password)
      .pipe(catchError((error) => throwError(() => new Error(error))))
      .pipe(map((token) => this.handleToken(token)));
  }

  /**
   * If the user is logged in, log them out and redirect them to the login page.
   */
  logout() {
    this.tokenStorage.clearToken();
    this.router.navigate([links.login.navigateTo]);
  }

  /**
   * It returns the token from the token storage.
   * @returns The token is being returned.
   */
  getToken(): string {
    return this.tokenStorage.getToken();
  }

  /**
   * If the token is not null, save it to the local storage and navigate to the home page.
   * @param {TokenResponse} token - TokenResponse - this is the response from the server.
   * @returns The token is being returned.
   */
  private handleToken(token: TokenResponse): TokenResponse {
    if (token) {
      this.tokenStorage.saveToken(token.token);
      this.router.navigate([links.home.navigateTo]);
      return token;
    }

    return null;
  }
}
