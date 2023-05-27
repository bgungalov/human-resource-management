import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';

import { UserRolesEditorComponent } from './user-roles-editor.component';

describe('UserRolesEditorComponent', () => {
  let component: UserRolesEditorComponent;
  let fixture: ComponentFixture<UserRolesEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [UserRolesEditorComponent],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MatDialog, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: {} },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(UserRolesEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
