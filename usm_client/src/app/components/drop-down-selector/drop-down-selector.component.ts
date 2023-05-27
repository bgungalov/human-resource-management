import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSelectChange } from '@angular/material/select';

interface MethodType {
  value: string;
  viewValue: string;
}

/**
 * Renders the dropdown selector component.
 */
@Component({
  selector: 'app-drop-down-selector',
  templateUrl: './drop-down-selector.component.html',
  styleUrls: ['./drop-down-selector.component.scss'],
})
export class DropDownSelectorComponent {
  @Input() control: FormControl;
  @Input() options: MethodType[];
  @Input() defaultValue: string;
  @Input() defaultLabel: string;

  @Output() selectionChange = new EventEmitter<MatSelectChange>();

  constructor() {}

  /**
   * When the selection changes, emit the event to the parent component.
   * @param event - The event that triggered the selection change.
   */
  onSelectionChange(event) {
    this.selectionChange.next(event);
  }
}
