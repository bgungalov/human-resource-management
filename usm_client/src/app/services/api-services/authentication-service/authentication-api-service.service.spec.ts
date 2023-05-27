import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AuthenticationApiServiceService } from './authentication-api-service.service';

describe('AuthenticationApiServiceService', () => {
  let service: AuthenticationApiServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule] });
    service = TestBed.inject(AuthenticationApiServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
