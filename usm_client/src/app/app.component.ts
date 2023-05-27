import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

/**
 * This class render the App component
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'User Management Service';

  /**
   * The constructor function is used to register the svg icon with the matIconRegistry service, which
   * is provided by the Angular Material library.
   * @param {MatIconRegistry} matIconRegistry - This is the service that registers the icons.
   * @param {DomSanitizer} domSanitzer - This is a service that Angular provides to sanitize HTML.
   */
  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitzer: DomSanitizer
  ) {
    this.matIconRegistry.addSvgIcon(
      'excel-icon',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        '../assets/icons/excel-icon.svg'
      )
    );
    this.matIconRegistry.addSvgIcon(
      'hr-logo',
      this.domSanitzer.bypassSecurityTrustResourceUrl(
        '../assets/icons/hr-logo.svg'
      )
    );
  }
}
