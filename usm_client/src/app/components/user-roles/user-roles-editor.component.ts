import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { DialogData } from 'src/app/models/dialog-data.model';
import { RoleRequest } from 'src/app/models/role-request.model';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { CalendarService } from 'src/app/services/calendar/calendar.service';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';

/**
 * Renders the user roles editor component.
 * We can select date range for the new role and assign it to the user.
 */
@Component({
  selector: 'app-user-roles-editor',
  templateUrl: './user-roles-editor.component.html',
  styleUrls: ['./user-roles-editor.component.scss'],
})
export class UserRolesEditorComponent implements OnInit, OnDestroy {
  range = new FormGroup({
    start: new FormControl<Date | null>(this.calendar.getPreviousDay()),
    end: new FormControl<Date | null>(this.calendar.getToday()),
  });

  roleName: string;
  apiSubscription: Subscription;
  userId: number;
  roleRequest: RoleRequest;
  minDate = this.calendar.getPreviousDay();
  isChange: boolean = false;

  rolesOptions = [
    { value: 'ROLE_ADMIN', viewValue: 'ROLE_ADMIN' },
    { value: 'ROLE_ADMIN_HELPER', viewValue: 'ROLE_ADMIN_HELPER' },
    { value: 'ROLE_BASIC_USER', viewValue: 'ROLE_BASIC_USER' },
  ];

  selectorDefaultValue = this.rolesOptions[2].value;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public dialogData: DialogData,
    public dialogRef: MatDialogRef<UserRolesEditorComponent>,
    private apiService: UserApiService,
    private calendar: CalendarService
  ) {}

  /**
   * Collect the needed role data for the API request.
   * @returns role data.
   */
  setRoleRequest(): RoleRequest {
    if (this.roleName === undefined) {
      this.roleName = this.selectorDefaultValue;
    }

    return (this.roleRequest = {
      userId: this.userId,
      roleName: this.roleName,
      startDate: this.calendar.formatDateMMDDYYYY(
        this.range.get('start').value
      ),
      endDate: this.calendar.formatDateMMDDYYYY(this.range.get('end').value),
    });
  }

  /**
   * Send the new role data to the API for creating a new role.
   */
  onClickSaveRoles() {
    this.apiSubscription = this.apiService
      .assignRoleToUser(this.setRoleRequest())
      .subscribe({
        next: (data) => {
          this.isChange = true;
          this.closeDialog();
        },
        error: (err) => {},
      });
  }

  /**
   * Loads the new selected role name.
   * @param event current event for the role selection.
   */
  onSelectionChange(event) {
    this.roleName = event.value;
  }

  /**
   * Close the dialog window.
   */
  closeDialog(): void {
    this.dialogRef.close(this.isChange);
  }

  /**
   * Loads the user id from the user details component.
   */
  ngOnInit(): void {
    this.userId = this.dialogData.dialogContent;
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);
  }
}
