import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';

/**
 * Service for retrieving health check data from API.
 */
@Injectable({
  providedIn: 'root',
})
export class HealthApiService {
  constructor(private httpClient: HttpClient) {}

  /**
   * HTTP GET METHOD
   * It returns an observable of type any
   * @returns Observable<any>
   */
  getHealthCheck(): Observable<any> {
    return this.httpClient.get(
      `${environment.userApi}actuator/health`
    ) as Observable<any>;
  }

  /**
   * HTTP GET METHOD
   * It returns an Observable of type any
   * @returns Observable<any>
   */
  getHealthCheckCustom(): Observable<any> {
    return this.httpClient.get(
      `${environment.userApi}actuator/health/custom`
    ) as Observable<any>;
  }
}
