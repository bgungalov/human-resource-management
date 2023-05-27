import { links } from 'src/app/utils/link-constants';
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../services/storage/token-storage.service';

/* A guard that checks if the user is logged in or not. */
@Injectable({
  providedIn: 'root',
})
export class AuthenticationGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {}

  /**
   * If the user has a token, return true, otherwise navigate to the login page and return false.
   * @param {ActivatedRouteSnapshot} route - ActivatedRouteSnapshot - The route that is being accessed.
   * @param {RouterStateSnapshot} state - RouterStateSnapshot
   */
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    const token = this.tokenStorage.getToken();

    if (token) {
      return true;
    }
    this.router.navigate([links.login.navigateTo]);
    return false;
  }
}
