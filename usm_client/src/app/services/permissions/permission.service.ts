import { Injectable } from '@angular/core';
import { Permissions } from 'src/app/models/permissions.model';
import { TokenStorageService } from '../storage/token-storage.service';

/* This class is a service that provides a method to check if a user has a permission. */
@Injectable({
  providedIn: 'root',
})
export class PermissionService {
  constructor(private tokenStorage: TokenStorageService) {}

  /**
   * It takes an array of permissions as an argument, and returns true if the user has at least one of
   * the permissions in the array
   * @param {Permissions[]} permissions - Permissions[]
   * @returns A boolean value.
   */
  checkPermission(permissions: Permissions[]): boolean {
    let user = this.tokenStorage.decodeToken().user;
    if (user) {
    return permissions.includes(user.userRoles[0].authority as any)

      // Return if no common element exist
    
    }

    return false;
  }
}
