import { MatCardModule } from '@angular/material/card';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { DecodedToken } from 'src/app/models/decoded-token.model';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let tokenStorage = new TokenStorageService();
  tokenStorage.saveToken(
    'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiZ3NkQGdtaWwuY29tIiwiZXhwIjoxNjU5NjIyNzAwLCJ1c2VyIjp7InVzZXJuYW1lIjoiYmdzZEBnbWlsLmNvbSIsInVzZXJSb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwibmFtZSI6IkJpc2VyIE1pdGtvdiBHdW5nYWxvdiJ9LCJpYXQiOjE2NTk2MTU1MDB9.BlN4YW0SZV6z3WaxS8rcrahYNPPtGYSVFLeydMALqSB-yyu1UwmjpNkXe4V7I4bSqy4LaLx5USxUBeQbxlZp9A'
  );

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [MatCardModule],
      providers: [{ provide: TokenStorageService, useValue: tokenStorage }],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
