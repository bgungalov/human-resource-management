import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { links } from 'src/app/utils/link-constants';

/**
 * Renders page not found component.
 */
@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss'],
})
export class PageNotFoundComponent {
  constructor(private router: Router) {}

  /**
   * This function navigates to the home page.
   */
  goHome() {
    this.router.navigate([links.home.path]);
  }
}
