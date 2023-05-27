import { links } from 'src/app/utils/link-constants';
import { Component, HostListener, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { DataExportService } from 'src/app/services/data-export/data-export.service';
import { UserListComponent } from 'src/app/components/user-list/user-list.component';
import { exportToExcelMenu } from 'src/app/utils/menu-options';

/**
 * Renderes the read user component.
 * Here are shown all users in a grid view.
 * We can navigate to create new user page.
 */
@Component({
  selector: 'app-read-user',
  templateUrl: './read-users.component.html',
  styleUrls: ['./read-users.component.scss'],
})
export class ReadUserComponent {
  menuData: any[] = exportToExcelMenu();
  isTableView = false;
  gridColumns = 3;

  constructor(
    private router: Router,
    private dataExportService: DataExportService
  ) {}

  @ViewChild(UserListComponent) userListComponent: UserListComponent;

  onToggleChange(event) {
    this.isTableView = !this.isTableView;
  }

  action(value: any) {
    console.log(value, 'value');
    this.checkExportType(value);
  }

  checkExportType(exportType: number) {
    switch (exportType) {
      case 1:
        this.export();
        break;
      case 2:
        this.exportAll();
        break;
    }
  }

  /**
   * Redirect to the create new user page.
   */
  navigateToCreateUserPage() {
    this.router.navigate([links.createUser.navigateTo]);
  }

  export() {
    this.dataExportService.exportTableToFile('users-table', 'User_Data');
  }

  exportAll() {
    this.dataExportService.exportJsonToFile(
      this.userListComponent.users,
      'Users_Data_All'
    );
  }
}
