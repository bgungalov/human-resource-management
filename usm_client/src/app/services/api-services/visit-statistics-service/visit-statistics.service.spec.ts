import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { VisitStatisticsService } from './visit-statistics.service';

describe('VisitStatisticsService', () => {
  let service: VisitStatisticsService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(VisitStatisticsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
