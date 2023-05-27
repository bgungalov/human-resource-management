import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DropDownMenuOption } from 'src/app/models/dropdown-menu-option.model';
import { Notification } from 'src/app/models/notification.model';
import { ICONS_PATH } from 'src/app/utils/icon-constants';
import { notificationsMenu } from 'src/app/utils/menu-options';

/**
 * Renders the notifications list.
 */
@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.scss'],
})
export class NotificationListComponent implements OnInit {
  @Input() notifications: Notification[];
  @Output() action: EventEmitter<any> = new EventEmitter<any>();
  menuItems: DropDownMenuOption[];
  notificationsIcon = ICONS_PATH.notification;
  notificationActive = ICONS_PATH.notificationActive;
  hasNewNotification: boolean;
  newNotifactions: any;

  constructor() {}

  /**
   * The function takes a value as an argument, and emits that value to the parent component
   * @param {any} value - any - The value of the button.
   */
  onClick(value: any) {
    this.action.emit(value);
  }

  /**
   * It loads the data for the notifications menu
   * @param {Notification[]} [notifications] - An array of notifications.
   */
  loadData(notifications?: Notification[]) {
    this.menuItems = notificationsMenu(notifications || this.notifications);
    this.checkForNewNotifications();
  }

  ngOnInit(): void {
    this.loadData();
  }

  /**
   * If there is a notification with a new property of true, then set the hasNewNotification property to
   * true. Otherwise, set it to false
   */
  private checkForNewNotifications() {
    if (this.notifications.find((notification) => notification.new === true)) {
      this.hasNewNotification = true;
    } else {
      this.hasNewNotification = false;
    }
  }
}
