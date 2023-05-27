import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Actions } from 'src/app/models/actions.model';
import { environment } from 'src/environments/environment.prod';

/**
 * Service for retrieving action statistics data from API
 */
@Injectable({
  providedIn: 'root',
})
export class ActionsApiService {
  constructor(private httpClient: HttpClient) {}

  /**
   * Retrieve user statistics data from API.
   * @returns all actions.
   */
  getActions(): Observable<Actions[]> {
    return this.httpClient.get(
      `${environment.userApi}useractions`
    ) as Observable<Actions[]>;
  }
}
