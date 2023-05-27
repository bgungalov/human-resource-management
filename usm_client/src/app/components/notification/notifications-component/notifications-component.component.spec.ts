import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NotificationsComponentComponent } from './notifications-component.component';

describe('NotificationsComponentComponent', () => {
  let component: NotificationsComponentComponent;
  let fixture: ComponentFixture<NotificationsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [NotificationsComponentComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NotificationsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
