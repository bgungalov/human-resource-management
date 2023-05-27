import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { Notification } from 'src/app/models/notification.model';
import { NotificationService } from 'src/app/services/api-services/notification-service/notification.service';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';
import { DropDownMenuOption } from 'src/app/models/dropdown-menu-option.model';
import { notificationsMenu } from 'src/app/utils/menu-options';
import { links } from 'src/app/utils/link-constants';
import { ICONS_PATH } from 'src/app/utils/icon-constants';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { NotificationListComponent } from '../notification-list/notification-list.component';

/**
 * Renders the notification component.
 */
@Component({
  selector: 'app-notifications-component',
  templateUrl: './notifications-component.component.html',
  styleUrls: ['./notifications-component.component.scss'],
})
export class NotificationsComponentComponent implements OnInit, OnDestroy {
  @ViewChild(NotificationListComponent)
  notificationList: NotificationListComponent;
  notificationInterval: any;

  notifications: Notification[] = [];
  private currentUserId: number;
  private apiSubscription: Subscription;

  constructor(
    private notificationService: NotificationService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  /**
   * It takes a value from a dropdown menu, extracts the username from the value, finds the
   * notification that matches the value, updates the notification to be no longer new, and then
   * updates the notification data.
   * @param {DropDownMenuOption} value - DropDownMenuOption - this is the value of the dropdown menu
   * option that was selected
   */
  action(value: DropDownMenuOption) {
    const extractedUsername = this.getUsernameFromAction(value.label);
    const selectedNotification = this.findNewNotification(value);
    selectedNotification.new = false;
    this.updateNotificationData(selectedNotification, value, extractedUsername);
  }

  /**
   * Every 3 seconds, loadDataFromSource() is called, which makes a call to the backend to get the
   * latest notifications for the current user.
   */
  ngOnInit() {
    this.currentUserId = this.tokenStorage.decodeToken().user.id;
    this.loadDataFromSource();

    this.notificationInterval = setInterval(
      () => this.loadDataFromSource(),
      3000
    );
  }

  /**
   * If the component is destroyed, unsubscribe from the API subscription and clear the notification
   * interval.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);

    if (this.notificationInterval) {
      clearInterval(this.notificationInterval);
    }
  }

  /**
   * It takes a string, splits it into an array, then splits the second element of that array into
   * another array, and returns an object with the first and last name
   * @param {string} action - string - the action that was performed
   * @returns An object with two properties, firstName and lastName.
   */
  private getUsernameFromAction(action: string): any {
    const result = action.split(': ')[1].split(' ');

    return {
      firstName: result[0],
      lastName: result[1],
    };
  }

  /**
   * Load notifications by receiverId
   */
  private loadDataFromSource() {
    this.apiSubscription = this.notificationService
      .getNotificationsByReceiverId(this.currentUserId)
      .subscribe({
        next: (data) => {
          this.notifications = data;
          if (this.notificationList) {
            this.notificationList.loadData(this.notifications);
          }
        },
      });
  }

  /**
   * It takes a DropDownMenuOption as an argument and returns a notification object that has a senderId
   * that matches the action property of the DropDownMenuOption.
   * @param {DropDownMenuOption} value - DropDownMenuOption - this is the value that is passed in from
   * the dropdown menu
   * @returns The first element in the array that satisfies the provided testing function. Otherwise
   * undefined is returned.
   */
  private findNewNotification(value: DropDownMenuOption) {
    return this.notifications.find(
      (notification) => notification.senderId === value.action
    );
  }

  /**
   * It updates the notification data and then navigates to the chat room.
   * @param {Notification} selectedNotification - Notification - this is the notification object that
   * is being updated
   * @param {DropDownMenuOption} value - DropDownMenuOption - this is the value of the dropdown menu
   * option that the user selected.
   * @param {any} extractedUsername - any
   */
  private updateNotificationData(
    selectedNotification: Notification,
    value: DropDownMenuOption,
    extractedUsername: any
  ) {
    this.notificationService
      .updateNotification(selectedNotification.id, selectedNotification)
      .subscribe((notification) =>
        this.router.navigate([
          links.chatRoom.navigateTo,
          value.action,
          extractedUsername.firstName,
          extractedUsername.lastName,
        ])
      );
  }
}
