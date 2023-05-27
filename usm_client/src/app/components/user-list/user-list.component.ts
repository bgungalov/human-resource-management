import { links } from 'src/app/utils/link-constants';
import { ClickableCell } from './../../models/clickable-cell.model';
import { Router } from '@angular/router';
import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { TableColumn } from 'src/app/models/table-column.model';
import { User } from 'src/app/models/user.model';
import { getUserColumns } from 'src/app/utils/table-columns';
// import { UserTestData } from './testData';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { Subscription } from 'rxjs';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';
import { Permissions } from 'src/app/models/permissions.model';

/**
 * Renders the user list component.
 * Loads the users list into the grid.
 */
@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit, OnDestroy, OnChanges {
  users: User[];
  columns: TableColumn[] = getUserColumns();
  apiSubscription: Subscription;
  @Input() isTableView;
  gridColumns = 3;
  pagedUsers;
  defaultSelectedPage = 0;
  defaultPageSize = 10;
  dataTotalSize;
  hasPermission = false;

  constructor(
    private router: Router,
    private apiService: UserApiService,
    private tokenService: TokenStorageService
  ) {}
  ngOnChanges(changes: SimpleChanges): void {
    this.getUsersPagination();
  }

  toggleGridColumns() {
    this.gridColumns = this.gridColumns === 3 ? 4 : 3;
  }

  /**
   * Handling the clickable cell in the grid.
   * @param event which is passed.
   */
  handleCellClick(event: ClickableCell) {
    switch (event.column.dataKey) {
      case this.columns[0].dataKey:
        this.router.navigate([links.userDetails.navigateTo, event.value.id]);
        break;
      case this.columns[1].dataKey:
        console.log(event.value.firstName);
        break;
      case this.columns[2].dataKey:
        console.log(event.value.lastName);
        break;
    }
  }

  navigateToUserDetails(userId) {
    this.router.navigate([links.userDetails.navigateTo, userId]);
  }

  /**
   * Get all users from API and load them to the grid.
   */
  ngOnInit(): void {
    this.apiSubscription = this.apiService
      .getUsers()
      .subscribe((data) => (this.users = data));

    if (
      this.tokenService.decodeToken().user.userRoles[0].authority !==
      Permissions.basicUser
    ) {
      this.hasPermission = true;
    }
  }

  scrollToTop() {
    window.scroll({
      top: 0,
      left: 0,
      behavior: 'smooth',
    });
  }

  getUsersPagination(pageNumber?, pageSize?) {
    this.scrollToTop();
    if (this.isTableView === false) {
      if (pageNumber >= 0 && pageSize >= 0) {
        this.defaultSelectedPage = pageNumber;
        this.defaultPageSize = pageSize;
      }
      this.apiSubscription = this.apiService
        .getUsersPagination(this.defaultSelectedPage, this.defaultPageSize)
        .subscribe((data: any) => {
          this.pagedUsers = data.content;
          data.totalElements = this.dataTotalSize = data.totalElements;
        });
    }
  }

  onPaginateChange(event) {
    this.getUsersPagination(event.pageIndex, event.pageSize);
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    this.apiSubscription.unsubscribe();
  }
}
