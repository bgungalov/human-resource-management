import { RouterTestingModule } from '@angular/router/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionsStatisticsComponent } from './actions-statistics.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ActionsStatisticsComponent', () => {
  let component: ActionsStatisticsComponent;
  let fixture: ComponentFixture<ActionsStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActionsStatisticsComponent],
      imports: [RouterTestingModule, HttpClientTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ActionsStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
