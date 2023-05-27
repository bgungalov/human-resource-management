import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

import { ChatRoomComponent } from './chat-room.component';

describe('ChatRoomComponent', () => {
  let component: ChatRoomComponent;
  let fixture: ComponentFixture<ChatRoomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule],
      declarations: [ChatRoomComponent],
      providers: [{ provide: FormBuilder }],
    }).compileComponents();

    fixture = TestBed.createComponent(ChatRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    try {
      fixture.destroy();
    } catch (e) {
      console.error(
        'Error during cleanup of component',
        fixture.componentInstance
      );
      console.error('Error during cleanup of component', {
        component: fixture.componentInstance,
        stacktrace: e,
      });
    }
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
