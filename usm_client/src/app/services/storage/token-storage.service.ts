import { Injectable } from '@angular/core';
import { TokenResponse } from 'src/app/models/token-respone.model';
import jwt_decode from 'jwt-decode';
import { DecodedToken } from 'src/app/models/decoded-token.model';

/* This class is used to store the JWT token in the browser's local storage. */
@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  key = 'token';

  constructor() {}

  /**
   * It returns the value of the key (token) in localStorage.
   * @returns The token is being returned.
   */
  getToken(): string {
    return localStorage.getItem(this.key);
  }

  /**
   * It takes a string as an argument and saves it to local storage.
   * @param {string} newToken - The new token to save.
   */
  saveToken(newToken: string): void {
    localStorage.setItem(this.key, newToken);
  }

  /**
   * If the token is valid JSON, return the token, otherwise return undefined.
   * @param {string} token - The token to be decoded.
   * @returns The token is being returned.
   */
  deserializeToken(token: string): TokenResponse {
    try {
      return JSON.parse(token);
    } catch (e) {
      return undefined;
    }
  }

  /**
   * If the token is valid, return the decoded token, otherwise return undefined.
   * @returns The decoded token.
   */
  decodeToken(): DecodedToken {
    try {
      return jwt_decode(this.getToken());
    } catch (Error) {
      return undefined;
    }
  }

  /**
   * It removes the token from local storage.
   */
  clearToken(): void {
    localStorage.removeItem(this.key);
  }
}
