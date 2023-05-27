import { Component, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { DialogComponent } from 'src/app/components/dialog/dialog.component';
import { DialogData } from 'src/app/models/dialog-data.model';
import { UserApiService } from 'src/app/services/api-services/user-service/user-api.service';
import { links } from 'src/app/utils/link-constants';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';

/**
 * Renders the delete user component.
 * We can delete user.
 */
@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.scss'],
})
export class DeleteUserComponent implements OnDestroy {
  apiSubscription: Subscription;
  panelOpenState = false;
  userId: number;
  dialogSubscription: Subscription;

  constructor(
    public dialog: MatDialog,
    private router: Router,
    private apiService: UserApiService,
    private activatedRoute: ActivatedRoute
  ) {}

  /**
   * It opens dialog component before deleting the user
   * and ask user to confirm his action.
   * On positive confirmation we call deleteUser().
   */
  dialogConfirmDeleteUser() {
    unsubscribeFrom(this.dialogSubscription);
    const dialog: DialogData = {
      dialogHeader: 'Are you sure you want to delete this user?',
      dialogContent: '',
      cancelButtonLabel: 'Cancel',
      confirmButtonLabel: 'Delete',
    };
    let currentDialog = this.dialog.open(DialogComponent, {
      width: '20%',
      data: dialog,
    });
    this.dialogSubscription = currentDialog
      .afterClosed()
      .subscribe((answer) => {
        if (answer) {
          this.deleteUser();
        }
      });
  }

  /**
   * Sends to the API the user id that need to be deleted.
   * If request succeeds, the user will be redirected to the
   * read users page.
   */
  deleteUser() {
    this.userId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.apiSubscription = this.apiService
      .deleteUserById(this.userId)
      .subscribe({
        next: (data) => this.router.navigate([links.readUsers.navigateTo]),
        error: (error) => {
          console.log(error);
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
