import { links } from 'src/app/utils/link-constants';
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { PermissionService } from '../services/permissions/permission.service';

@Injectable({
  providedIn: 'root',
})
export class PermissionsGuard implements CanActivate {
  constructor(
    private permissionService: PermissionService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const permissionGtanted = this.permissionService.checkPermission(
      route.data['permission']
    );

    if (permissionGtanted) {
      return permissionGtanted;
    } else {
      this.router.navigate([links.permissionDenied.navigateTo]);
      return false;
    }
  }
}
