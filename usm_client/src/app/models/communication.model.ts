import { Attendance } from './attendance.model';
import { Message } from './message.model';

export class Communication {
  communicationId: number;
  attendance: Attendance[];
  messages: Message[];

  constructor(
    communicationId: number,
    attendance: Attendance[],
    messages?: Message[]
  ) {
    this.communicationId = communicationId;
    this.attendance = attendance;
    this.messages = messages;
  }
}
