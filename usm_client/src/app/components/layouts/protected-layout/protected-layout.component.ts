import { AppLink } from './../../../models/app-link.model';
import { Component, HostListener, OnInit } from '@angular/core';
import { links } from 'src/app/utils/link-constants';
import { AuthenticationService } from 'src/app/services/authentication-service/authentication.service';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';

/**
 * Renders protected layout component.
 */
@Component({
  selector: 'app-protected-layout',
  templateUrl: './protected-layout.component.html',
  styleUrls: ['./protected-layout.component.scss'],
})
export class ProtectedLayoutComponent {
  isCollapsed = true;
  appLinks: AppLink[];

  change_menu_icon: boolean = true;
  isShow: boolean;
  topPosToStartShowing = 100;

  decodedToken = this.tokenStorage.decodeToken();
  tokenUserFirstName =
    `${this.decodedToken.user.firstName}`;

  constructor(private authenticationService: AuthenticationService,private tokenStorage: TokenStorageService) {
    this.appLinks = [
      links.home,
      links.readUsers,
      // links.settings,
      links.actionsDashboard,
      links.statusDetails,
    ];
  }

  /**
   * This calls the logout() in authentication service component.
   * This is the logic for logout button.
   */
  handleLogout() {
    this.authenticationService.logout();
  }

  @HostListener('window:scroll')
  checkScroll() {
    // window의 scroll top
    // Both window.pageYOffset and document.documentElement.scrollTop returns the same result in all the cases. window.pageYOffset is not supported below IE 9.

    const scrollPosition =
      window.pageYOffset ||
      document.documentElement.scrollTop ||
      document.body.scrollTop ||
      0;

    if (scrollPosition >= this.topPosToStartShowing) {
      this.isShow = true;
    } else {
      this.isShow = false;
    }
  }

  // TODO: Cross browsing
  gotoTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth',
    });
  }

  onCollapse() {
    if (this.isCollapsed) {
      this.isCollapsed = false;
    }

    this.isCollapsed = true;
  }
}
