import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommunicationApiService } from '../../services/api-services/communication-service/communication-api.service';
import { Communication } from './../../models/communication.model';
import {
  AfterViewChecked,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { CommunicationRequest } from 'src/app/models/communication-request.model';
import { Subscription } from 'rxjs';
import { Message } from 'src/app/models/message.model';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { CalendarService } from 'src/app/services/calendar/calendar.service';
import { WebsocketService } from 'src/app/services/websocket-service/websocket.service';
import { links } from 'src/app/utils/link-constants';

/**
 * Renders the chat room component.
 */
@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.scss'],
})
export class ChatRoomComponent implements OnInit, AfterViewChecked, OnDestroy {
  communication: Communication;
  currentUser: CommunicationRequest;
  otherUser: CommunicationRequest;
  apiSubscription: Subscription;
  currentDate: Date;

  @ViewChild('scroll') private scrollContainer: ElementRef;

  constructor(
    private communicationApiService: CommunicationApiService,
    private activatedRoute: ActivatedRoute,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder,
    private calendarService: CalendarService,
    private websocketService: WebsocketService,
    private router: Router
  ) {}

  /**
   * It scrolls down the messages container.
   */
  scrollToBottom(): void {
    try {
      this.scrollContainer.nativeElement.scrollTop =
        this.scrollContainer.nativeElement.scrollHeight;
    } catch (err) {}
  }

  navigateToUserDetails(userId){
    this.router.navigate([links.userDetails.navigateTo, userId]);
  }

  /**
   * When the user clicks the send button, the message is sent to the websocket service, which then sends
   * it to the server, which then sends it to the other user, who then receives it.
   */
  submitMessage() {
    const sendMessage = {
      ...new Message(
        this.currentUser.id,
        this.otherUser.id,
        this.sendMessageForm.get('message').value,
        true,
        this.calendarService.getToday(),
        null,
        this.communication
      ),
    };

    if (sendMessage) {
      this.websocketService.messages.next(sendMessage);
    }

    this.sendMessageForm.reset();
  }

  /* Creating a form group with a message field that is required and has a pattern. */
  sendMessageForm: FormGroup = this.formBuilder.group({
    message: [
      '',
      [Validators.required, Validators.pattern(/^(\s+\S+\s*)*(?!\s).*$/)],
    ],
  });

  /**
   * It takes the decoded token from the token storage service and returns a new CommunicationRequest
   * object with the user's id, first name, and last name.
   * @returns A new CommunicationRequest object.
   */
  private loadCurrentUser(): CommunicationRequest {
    const decodedToken = this.tokenStorage.decodeToken();
    return new CommunicationRequest(
      decodedToken.user.id,
      decodedToken.user.firstName,
      decodedToken.user.lastName
    );
  }

  /**
   * It takes the userId, firstName, and lastName from the URL and creates a new CommunicationRequest
   * object.
   * @returns A new instance of the CommunicationRequest class.
   */
  private loadOtherUser(): CommunicationRequest {
    const id = +this.activatedRoute.snapshot.paramMap.get('userId');
    const firstName = this.activatedRoute.snapshot.paramMap.get('firstName');
    const lastName = this.activatedRoute.snapshot.paramMap.get('lastName');
    return new CommunicationRequest(id, firstName, lastName);
  }

  /**
   * The function initializes the communication between two users and subscribes to the websocket
   * service to receive messages.
   */
  ngOnInit() {
    this.currentUser = this.loadCurrentUser();
    this.otherUser = this.loadOtherUser();

    this.apiSubscription = this.communicationApiService
      .initializeCommunication([this.currentUser, this.otherUser])
      .subscribe({
        next: (data) => {
          this.communication = new Communication(
            data.communicationId,
            data.attendance,
            data.messages
          );

          this.websocketService.initializeWebsocket(data.communicationId);

          if (this.websocketService.messages) {
            this.websocketService.messages.subscribe({
              next: (message) => {
                if (message) {
                  this.communication.messages.push(
                    new Message(
                      message.senderId,
                      message.receiverId,
                      message.body,
                      message.isNew,
                      message.updateDate,
                      message.messageId,
                      message.communication
                    )
                  );
                }
              },
            });
          }
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  /**
   * After the view is checked, scroll to the bottom of the page.
   */
  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  /**
   * When componenet is destroyed and if the subscription is not null,
   * then unsubscribe from it.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);
    this.websocketService.disconnect();
  }
}
