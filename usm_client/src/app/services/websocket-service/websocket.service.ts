import { Injectable } from '@angular/core';
import { Subject, map, Observable, Observer } from 'rxjs';
import { AnonymousSubject } from 'rxjs/internal/Subject';
import { Message } from 'src/app/models/message.model';
import { environment } from 'src/environments/environment.prod';

/* We're creating a service that will allow us to connect to a websocket server, and then send and
receive messages from that server. */
@Injectable({
  providedIn: 'root',
})
export class WebsocketService {
  public messages: Subject<Message>;
  private subject: AnonymousSubject<MessageEvent>;

  private socket: WebSocket;

  constructor() {}

  public disconnect() {
    this.socket.close();
  }

  /**
   * It creates a new WebSocket connection to the server, and returns a Subject that will emit the
   * messages received from the server
   * @param url - The url of the websocket server.
   */
  initializeWebsocket(url) {
    this.messages = <Subject<Message>>this.connect(
      `${environment.chatUrl}${url}`
    ).pipe(
      map((response: MessageEvent): any => {
        try {
          return JSON.parse(response.data) as Message;
        } catch (skip) {
          return undefined;
        }
      })
    );
  }

  /**
   * If the subject is not defined, create a new subject and return it
   * @param url - The URL of the WebSocket server.
   * @returns An observable
   */
  connect(url): AnonymousSubject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
    }
    return this.subject;
  }

  /**
   * It creates a new WebSocket connection, and returns an AnonymousSubject that wraps the WebSocket's
   * observer and observable
   * @param url - The url of the websocket server.
   * @returns An AnonymousSubject is being returned.
   */
  private create(url): AnonymousSubject<MessageEvent> {
    this.socket = new WebSocket(url);
    let observable = new Observable((obs: Observer<MessageEvent>) => {
      this.socket.onmessage = obs.next.bind(obs);
      this.socket.onerror = obs.error.bind(obs);
      this.socket.onclose = obs.complete.bind(obs);
      return this.socket.close.bind(this.socket);
    });
    let observer = {
      error: null,
      complete: null,
      next: (data: Object) => {
        if (this.socket.readyState === WebSocket.OPEN) {
          this.socket.send(JSON.stringify(data));
        }
      },
    };
    return new AnonymousSubject<MessageEvent>(observer, observable);
  }
}
