import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from 'src/app/models/dialog-data.model';
import { StateService } from 'src/app/services/shared/state.service';

/**
 * Renders the dialog component.
 */
@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public dialogData: DialogData,
    public stateService: StateService
  ) {}

  /**
   * Handles positive response on close.
   */
  handleDialogSubmit() {
    this.dialogRef.close(true);
  }

  /**
   * Handles negative response on close.
   */
  closeDialog(): void {
    this.dialogRef.close();
  }
}
