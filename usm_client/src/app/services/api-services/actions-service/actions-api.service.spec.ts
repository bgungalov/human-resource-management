import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { ActionsApiService } from './actions-api.service';

describe('ActionsApiService', () => {
  let service: ActionsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(ActionsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
