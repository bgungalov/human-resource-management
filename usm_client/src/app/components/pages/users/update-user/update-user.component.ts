import { Component, OnInit, OnDestroy, AfterViewChecked } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/models/user.model';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DialogData } from 'src/app/models/dialog-data.model';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { UserBuilder } from 'src/app/utils/user-builder';
import { UserDetails } from 'src/app/models/user-details.model';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';

/**
 * Renders update user component.
 * We can update user details.
 */
@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss'],
})
export class UpdateUserComponent implements OnInit, OnDestroy {
  user: User;
  apiSubscription: Subscription;
  dialogSubscription: Subscription;
  userId: number;
  panelOpenState = false;
  userBuilder: UserBuilder = new UserBuilder();

  constructor(
    public dialog: MatDialog,
    private location: Location,
    private apiService: UserApiService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  /**
   * User form for updating user information.
   */
  updateUserForm: FormGroup = this.formBuilder.group({
    firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z]{3,}')]],
    middleName: ['', [Validators.pattern('[a-zA-Z]{0,}')]],
    lastName: ['', [Validators.pattern('[a-zA-Z]{3,}')]],
    email: [
      '',
      [
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9-_]+@[a-zA-Z]+[.][^s@]{1,3}$'),
      ],
    ],
    phoneNumber: ['', [Validators.pattern('^[0-9-+]{9,}$')]],
    ucn: ['', [Validators.pattern('^\\d{10}')]],
    address: ['', [Validators.nullValidator]],
    country: ['', [Validators.nullValidator]],
    city: ['', [Validators.nullValidator]],
    cityPostCode: ['', [Validators.nullValidator]],
    street: ['', [Validators.nullValidator]],
  });

  /**
   * Sends to the API the new user information.
   * If the information validation fails, error will be shown.
   * If the information validation succeeds, the user will be
   * redirected to the currently selected user details page.
   */
  onUpdateUser() {
    if (this.updateUserForm.valid) {
      this.apiSubscription = this.apiService
        .updateUser(this.userId, this.setUserDetails())
        .subscribe({
          next: (data) => {
            data.id = this.userId;
          },
          error: (err) => {
            this.updateUserForm.setErrors({
              wrongDetails: true,
            });
          },
          complete: () => {
            this.onClickBackButton();
          },
        });
    }
  }

  /**
   * It opens dialog component before updating the user information
   * and ask user to confirm his action.
   * On positive confirmation we call onUpdateUser().
   */
  dialogConfirmUpdateUser() {
    const dialog: DialogData = {
      dialogHeader: 'Are you sure you want to update this user?',
      dialogContent: '',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Update',
    };
    let currentDialog = this.dialog.open(DialogComponent, {
      minWidth: 'fit-content',
      width: '20%',
      data: dialog,
    });
    this.dialogSubscription = currentDialog
      .afterClosed()
      .subscribe((answer) => {
        if (answer) {
          this.onUpdateUser();
        }
      });
  }

  /**
   * Redirect user to the previous page he came from.
   */
  onClickBackButton() {
    this.location.back();
  }

  /**
   * Loads the current available user information into the form fields.
   * @param user currently selected user to retrieve information from.
   */
  private loadData(user: User) {
    this.user = user;
    this.updateUserForm.get('firstName').setValue(user.firstName);
    this.updateUserForm.get('middleName').setValue(user.middleName);
    this.updateUserForm.get('lastName').setValue(user.lastName);
    this.updateUserForm.get('email').setValue(user.email);
    this.updateUserForm.get('phoneNumber').setValue(user.phoneNumber);
    this.updateUserForm.get('ucn').setValue(user.userDetails.ucn);
    this.updateUserForm.get('address').setValue(user.userDetails.address);
    this.updateUserForm.get('country').setValue(user.userDetails.country);
    this.updateUserForm.get('city').setValue(user.userDetails.city);
    this.updateUserForm
      .get('cityPostCode')
      .setValue(user.userDetails.cityPostCode);
    this.updateUserForm.get('street').setValue(user.userDetails.street);
  }

  /**
   * Sets the newly provided information for the user.
   * @returns user with new information.
   */
  private setUserDetails(): User {
    return this.userBuilder
      .setId(this.userId)
      .setFirstName(this.updateUserForm.get('firstName').value)
      .setMiddleName(this.updateUserForm.get('middleName').value)
      .setLastName(this.updateUserForm.get('lastName').value)
      .setEmail(this.updateUserForm.get('email').value)
      .setPhoneNumber(this.updateUserForm.get('phoneNumber').value)
      .setUserDetails(
        new UserDetails(
          this.updateUserForm.get('ucn').value,
          this.updateUserForm.get('address').value,
          this.updateUserForm.get('country').value,
          this.updateUserForm.get('city').value,
          this.updateUserForm.get('cityPostCode').value,
          this.updateUserForm.get('street').value
        )
      )
      .build();
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
        next: (userData) => this.loadData(userData),
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
