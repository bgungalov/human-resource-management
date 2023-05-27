import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserDetails } from 'src/app/models/user-details.model';
import { User } from 'src/app/models/user.model';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { UserBuilder } from 'src/app/utils/user-builder';
import { UserCredentials } from 'src/app/models/user-credentials.model';
import { encrypt } from 'caesar-shift';
import { links } from 'src/app/utils/link-constants';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { MatDialog } from '@angular/material/dialog';
import { DialogData } from 'src/app/models/dialog-data.model';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';

/**
 * Renders create use component.
 * We can create new user.
 */
@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss'],
})
export class CreateUserComponent implements OnDestroy {
  user: User;
  apiSubscription: Subscription;
  dialogSubscription: Subscription;

  userBuilder: UserBuilder = new UserBuilder();

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private apiService: UserApiService,
    private formBuilder: FormBuilder
  ) {}

  /**
   * Render create user form fields.
   */
  saveUserForm: FormGroup = this.formBuilder.group({
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
   * Dialog is shown when user saved the new user.
   * Dialog provides the new user login credentials.
   * Then navigates back to read user page.
   */
  dialogUserCredentials() {
    const dialogData: DialogData = {
      dialogHeader: 'User credentials details',
      dialogContent: `Username: ${this.setUserCredentials().username} `,
      dialogSecondContent: `Password: ${this.setUserCredentials().password}`,
      confirmButtonLabel: 'Ok',
    };
    let currentDialog = this.dialog.open(DialogComponent, {
      width: '20%',
      data: dialogData,
    });
    this.dialogSubscription = currentDialog
      .afterClosed()
      .subscribe((answer) => {
        if (answer) {
          this.onClickBackButton();
        }
      });
  }

  /**
   * Sends to the API the user information for
   * the new user that will be created.
   * Open the dialog with user credentials when
   * user clicked "Save" button.
   */
  onSaveUser() {
    if (this.saveUserForm.valid) {
      this.apiSubscription = this.apiService
        .createNewUser(this.setUserDetails(), this.setUserCredentials())
        .subscribe({
          next: (data) => {
            this.user = data;
          },
          error: (err) => {
            this.saveUserForm.setErrors({
              wrongDetails: true,
            });
          },
          complete: () => {
            this.dialogUserCredentials();
          },
        });
    }
  }

  /**
   * Navigate to read users page.
   */
  onClickBackButton() {
    this.router.navigate([links.readUsers.navigateTo]);
  }

  /**
   * Generate user credentials based on user information.
   * @returns newly created user credentials.
   */
  setUserCredentials() {
    let ucn = this.saveUserForm.get('ucn').value as string;
    let email = this.saveUserForm.get('email').value;
    const generatedPassword =
      encrypt(3, this.saveUserForm.get('firstName').value) +
      ucn.substring(ucn.length - 4);

    return new UserCredentials(email, generatedPassword, true);
  }

  /**
   * Loads the data from user form fields and
   * apply them to the new user.
   * @returns newly created user.
   */
  private setUserDetails(): User {
    return this.userBuilder
      .setFirstName(this.saveUserForm.get('firstName').value)
      .setMiddleName(this.saveUserForm.get('middleName').value)
      .setLastName(this.saveUserForm.get('lastName').value)
      .setEmail(this.saveUserForm.get('email').value)
      .setPhoneNumber(this.saveUserForm.get('phoneNumber').value)
      .setUserDetails(
        new UserDetails(
          this.saveUserForm.get('ucn').value,
          this.saveUserForm.get('address').value,
          this.saveUserForm.get('country').value,
          this.saveUserForm.get('city').value,
          this.saveUserForm.get('cityPostCode').value,
          this.saveUserForm.get('street').value
        )
      )
      .build();
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
