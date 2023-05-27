import { environment } from 'src/environments/environment.prod';
import { Communication } from '../../../models/communication.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommunicationRequest } from 'src/app/models/communication-request.model';

/**
 * Service for retrieving communication data from API.
 */
@Injectable({
  providedIn: 'root',
})
export class CommunicationApiService {
  constructor(private httpClient: HttpClient) {}

  /**
   * HTTP POST METHOD
   * This function takes an array of CommunicationRequest objects and returns an Observable of
   * Communication objects
   * @param {CommunicationRequest[]} users - CommunicationRequest[]
   * @returns An observable of type Communication
   */
  initializeCommunication(
    users: CommunicationRequest[]
  ): Observable<Communication> {
    return this.httpClient.post(
      `${environment.userApi}communication`,
      users
    ) as Observable<Communication>;
  }

  /**
   * HTTP GET METHOD
   * This function takes in a communicationId and returns an observable of type Communication
   * @param {number} communicationId - The id of the communication you want to get.
   * @returns An observable of type Communication
   */
  getCommunicationDataByCommunicationId(
    communicationId: number
  ): Observable<Communication> {
    let queryParams = new HttpParams().set('communicationId', communicationId);

    return this.httpClient.get(`${environment.userApi}communication/id`, {
      params: queryParams,
    }) as Observable<Communication>;
  }
}
