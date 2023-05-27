import { typeOfNotification } from '../utils/type-of-notification';

export interface Notification {
  id: number;
  senderId: number;
  sender: string;
  receiverId: number;
  body: string;
  type: typeOfNotification;
  createdDate: Date;
  new: boolean;
}
