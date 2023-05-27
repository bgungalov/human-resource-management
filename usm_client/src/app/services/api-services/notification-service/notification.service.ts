import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Notification } from 'src/app/models/notification.model';

/**
 * Service for notification API requests.
 */
@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor(private httpClient: HttpClient) {}

  /**
   * GET METHOD
   * This function is used to get all notifications by receiverId
   * @param {number} receiverId - The id of the user who is receiving the notification.
   * @returns An observable of type Notification.
   */
  getNotificationsByReceiverId(receiverId: number): Observable<Notification[]> {
    return this.httpClient.get(
      `${environment.userApi}notifications/all/${receiverId}`
    ) as Observable<Notification[]>;
  }

  /**
   * PUT METHOD
   * This function takes in a notificationId and a notification object and returns an observable of type
   * Notification
   * @param {number} notificationId - number - The id of the notification to update.
   * @param {Notification} notification - Notification - This is the notification object that we want to
   * update.
   * @returns Observable<Notification>
   */
  updateNotification(
    notificationId: number,
    notification: Notification
  ): Observable<Notification> {
    return this.httpClient.put(
      `${environment.userApi}notification/update/${notificationId}`,
      notification
    ) as Observable<Notification>;
  }
}
