import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenResponse } from 'src/app/models/token-respone.model';

/**
 * Service for API authentication handler
 */
@Injectable({
  providedIn: 'root',
})
export class AuthenticationApiServiceService {
  constructor(private httpClient: HttpClient) {}

  /**
   * POST METHOD
   * @param username current user username to login with
   * @param password current user password to login with
   * @returns JWT token
   */
  authenticate(username: string, password: string): Observable<TokenResponse> {
    const authenticateBody = {
      username: username,
      password: password,
    };

    return this.httpClient.post(
      `${environment.userApi}authenticate`,
      authenticateBody
    ) as Observable<TokenResponse>;
  }
}
