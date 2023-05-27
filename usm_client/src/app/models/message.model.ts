import { Communication } from './communication.model';

export class Message {
  senderId: number;
  receiverId: number;
  body: string;
  isNew: boolean;
  updateDate: Date;
  messageId: number;
  communication: Communication;

  constructor(
    senderId: number,
    receiverId: number,
    body: string,
    isNew: boolean,
    updateDate: Date,
    messageId: number,
    communication: Communication
  ) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.body = body;
    this.isNew = isNew;
    this.updateDate = updateDate;
    this.messageId = messageId;
    this.communication = new Communication(
      communication.communicationId,
      communication.attendance
    );
  }
}
