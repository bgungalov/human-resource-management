import { TokenStorageService } from './../../../services/storage/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { links } from 'src/app/utils/link-constants';
import { Router } from '@angular/router';
import { Permissions } from 'src/app/models/permissions.model';

/**
 * Renders home component.
 */
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  decodedToken = this.tokenStorage.decodeToken();
  userId = this.decodedToken.user.id;
  tokenUserFullName =
    `${this.decodedToken.user.firstName}` +
    ` ${this.decodedToken.user.lastName}`;
  tokenUserRoles = this.decodedToken.user.userRoles[0].authority;
  hasPermission = false;

  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}
  ngOnInit(): void {

    if (
      this.tokenStorage.decodeToken().user.userRoles[0].authority !==
      Permissions.basicUser
    ) {
      this.hasPermission = true;
    }
  }

  navigateToUserDetails(userId) {
    this.router.navigate([links.userDetails.navigateTo, userId]);
  }
}
