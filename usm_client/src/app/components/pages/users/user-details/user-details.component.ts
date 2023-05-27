import { DeleteUserComponent } from './../delete-user/delete-user.component';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { links } from 'src/app/utils/link-constants';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { DialogData } from 'src/app/models/dialog-data.model';
import { UserRolesEditorComponent } from 'src/app/components/user-roles/user-roles-editor.component';
import { MatDialog } from '@angular/material/dialog';
import { UserRole } from 'src/app/models/user-role.model';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { CalendarService } from 'src/app/services/calendar/calendar.service';

/**
 * Renders user details page.
 * We can delete or edit currently selected user.
 * We can call component to assign or edit user roles.
 * We can delete any role of currently selected user.
 */
@Component({
  providers: [DeleteUserComponent],
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss'],
})
export class UserDetailsComponent implements OnInit, OnDestroy {
  user: User;
  apiSubscription: Subscription;
  dialogSubscription: Subscription;
  panelOpenState = false;
  userId: number;
  roleId: number;
  foundCurrentRole: UserRole;

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private apiService: UserApiService,
    private activatedRoute: ActivatedRoute,
    private deleteUserComponent: DeleteUserComponent,
    private calendarService: CalendarService
  ) {}

  /**
   * It opens user edit roles component in dialog.
   */
  dialogEditUserRoles() {
    unsubscribeFrom(this.dialogSubscription);
    const dialogData: DialogData = {
      dialogHeader: 'Edit user Roles',
      dialogContent: this.userId,
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Delete',
    };
    let currentDialog = this.dialog.open(UserRolesEditorComponent, {
      minWidth: 'fit-content',
      width: '50%',
      data: dialogData,
    });
    this.dialogSubscription = currentDialog
      .afterClosed()
      .subscribe((answer) => {
        if (answer) {
          unsubscribeFrom(this.apiSubscription);
          this.ngOnInit();
        }
      });
  }

  openMessages(): void {
    this.router.navigate([
      `${links.chatRoom.navigateTo}`,
      this.user.id,
      this.user.firstName,
      this.user.lastName,
    ]);
  }

  /**
   * Sends to the API user id and selected role id to be terminated.
   * Then reloads the data calling ngOnInit().
   * @param role currently selected role that will be terminated.
   */
  terminateUserRole(role: UserRole) {
    this.apiSubscription = this.apiService
      .unassignUserRole(this.userId, role.id)
      .subscribe({
        next: (answer) => {
          if (answer) {
            unsubscribeFrom(this.apiSubscription);
            this.ngOnInit();
          }
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  /**
   * It opens dialog component before terminating the role
   * and ask user to confirm his action.
   * On positive confirmation we call terminateUserRole()
   * passing currently selected role that will be terminated.
   * @param role currently selected role that will be terminated.
   */
  confirmTerminateUserRole(role: UserRole) {
    const dialogData: DialogData = {
      dialogHeader: 'Are you sure you want to terminate this role?',
      dialogContent: `Terminate ${role.roleName}?`,
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Terminate',
    };
    let currentDialog = this.dialog.open(DialogComponent, {
      minWidth: 'fit-content',
      width: '20%',
      data: dialogData,
    });
    this.dialogSubscription = currentDialog.afterClosed().subscribe({
      next: (answer) => {
        if (answer) {
          unsubscribeFrom(this.apiSubscription);
          this.terminateUserRole(role);
        }
      },
    });
  }

  /**
   * Finds the current active role if it exists.
   * @returns active current role if it exists else returns undefined.
   */
  findCurrentRole(): UserRole {
    let today = this.calendarService.getToday();

    return this.user.roles.find((role) => {
      const [startDate, endDate] = [
        this.calendarService.getDate(
          this.calendarService.formatStringToDateTime(role.startDate)
        ),
        this.calendarService.getDate(
          this.calendarService.formatStringToDateTime(role.endDate)
        ),
      ];

      if (startDate < today && endDate > today) {
        return role;
      } else {
        return undefined;
      }
    });
  }

  /**
   * Calls child component DeleteUserComponent to open confirmation
   * dialog for deletion of currently selected user.
   */
  deleteUser() {
    this.deleteUserComponent.dialogConfirmDeleteUser();
  }

  /**
   * Navigate to user update page by passing the currently selected user id.
   */
  navigateToUpdateUser() {
    this.router.navigate([links.updateUser.navigateTo, this.userId]);
  }

  /**
   * Extracts user id from path variable from the current url.
   * Loads the data from the API of the currently selected user.
   */
  ngOnInit(): void {
    this.userId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.apiSubscription = this.apiService
      .getExactUsers(this.userId)
      .subscribe({
        next: (userData) => {
          this.user = userData;
          this.foundCurrentRole = this.findCurrentRole();
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);
    unsubscribeFrom(this.dialogSubscription);
  }
}
