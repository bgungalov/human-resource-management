import { VisitStatistics } from '../../../models/visit-statistics.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
import { CalendarService } from '../../calendar/calendar.service';

/**
 * Service for statistics API requests.
 */
@Injectable({
  providedIn: 'root',
})
export class VisitStatisticsService {
  constructor(
    private httpClient: HttpClient,
    private calendar: CalendarService
  ) {}

  /**
   * Get statistics data from API.
   * @param methodType request parameter for method type.
   * @param startDate request parameter for start date.
   * @param endDate request parameter for method end date.
   * @returns retrieved statistics data.
   */
  getStatistics(
    methodType: string,
    startDate: Date,
    endDate: Date
  ): Observable<VisitStatistics[]> {
    let queryParams = new HttpParams()
      .set('methodType', methodType)
      .set('startDate', this.calendar.formatDateMMDDYYYY(startDate))
      .set('endDate', this.calendar.formatDateMMDDYYYY(endDate));

    return this.httpClient.get(`${environment.userApi}statistics`, {
      params: queryParams,
    }) as Observable<VisitStatistics[]>;
  }
}
