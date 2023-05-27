import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { CommunicationApiService } from './communication-api.service';

describe('CommunicationApiService', () => {
  let service: CommunicationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(CommunicationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
