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

/* It checks if the user is logged in or not. */
@Injectable({
  providedIn: 'root',
})
export class PublicGuard implements CanActivate {
  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {}

  /**
   * If there is no token, return true, otherwise navigate to the home page and return false.
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

    if (!token) {
      return true;
    }
    this.router.navigate([links.home.navigateTo]);
    return false;
  }
}
