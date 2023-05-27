import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { TableColumn } from '../../models/table-column.model';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

/* A component class that is used to create a table. */
@Component({
  selector: 'app-custom-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit, AfterViewInit {
  public tableDataSource = new MatTableDataSource([]);
  public displayedColumns: string[];
  @ViewChild(MatPaginator, { static: false }) matPaginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) matSort: MatSort;

  @Input() isPageable = false;
  @Input() isSortable = false;
  @Input() isFilterable = false;
  @Input() tableColumns: TableColumn[] = [];
  @Input() rowActionIcon: string;
  @Input() paginationSizes: number[] = [5, 10, 15];
  @Input() defaultPageSize = this.paginationSizes[1];

  @Output() sort: EventEmitter<Sort> = new EventEmitter();
  @Output() rowAction: EventEmitter<any> = new EventEmitter<any>();
  @Output() clickCellAction: EventEmitter<any> = new EventEmitter<any>();

  // this property needs to have a setter, to dynamically get changes from parent component
  @Input() set tableData(data: any[]) {
    this.setTableDataSource(data);
  }

  constructor() {}

  /**
   * Maps column names to column values.
   */
  ngOnInit(): void {
    const columnNames = this.tableColumns.map(
      (tableColumn: TableColumn) => tableColumn.name
    );
    if (this.rowActionIcon) {
      this.displayedColumns = [this.rowActionIcon, ...columnNames];
    } else {
      this.displayedColumns = columnNames;
    }
  }

  /**
   * Enable pagination works with *ngIf.
   */
  ngAfterViewInit(): void {
    this.tableDataSource.paginator = this.matPaginator;
  }

  /**
   * This function takes in an array of data, and sets the tableDataSource to a new MatTableDataSource
   * of that data, and then sets the paginator and sort to the MatPaginator and MatSort that are
   * defined in the HTML.
   * @param {any} data - any - this is the data that you want to display in the table.
   */
  setTableDataSource(data: any) {
    this.tableDataSource = new MatTableDataSource<any>(data);
    this.tableDataSource.paginator = this.matPaginator;
    this.tableDataSource.sort = this.matSort;
  }

  /**
   * The function takes the event object as an argument, and then uses the event object to get the value
   * of the input field.
   * The value of the input field is then used to filter the table data.
   * @param {Event} event - Event - The event that triggered the function.
   */
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.tableDataSource.filter = filterValue.trim().toLowerCase();
  }

  /**
   * Sort table data.
   * @param sortParameters sort parameters for sorting the table.
   */
  sortTable(sortParameters: Sort) {
    // defining name of data property, to sort by, instead of column name
    sortParameters.active = this.tableColumns.find(
      (column) => column.name === sortParameters.active
    ).dataKey;
    this.sort.emit(sortParameters);
  }

  /**
   * Row action
   * @param row current row to perform action on.
   */
  emitRowAction(row: any) {
    this.rowAction.emit(row);
  }

  /**
   * If the tableColumn is clickable, emit the value of the element and the column.
   * @param {any} element - any - the row of data
   * @param {TableColumn} tableColumn - TableColumn - this is the column object that is passed in from
   * the parent component
   */
  handleClick(element: any, tableColumn: TableColumn) {
    if (tableColumn.isClickable) {
      this.clickCellAction.emit({ value: element, column: tableColumn });
    }
  }
}
