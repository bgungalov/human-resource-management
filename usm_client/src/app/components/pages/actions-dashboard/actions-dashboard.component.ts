import { links } from 'src/app/utils/link-constants';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Sort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Actions } from 'src/app/models/actions.model';
import { TableColumn } from 'src/app/models/table-column.model';
import { ActionsApiService } from 'src/app/services/api-services/actions-service/actions-api.service';
import { getActionsColumns } from 'src/app/utils/table-columns';
import * as XLSX from 'xlsx';
import { DataExportService } from 'src/app/services/data-export/data-export.service';
import { exportToExcelMenu } from 'src/app/utils/menu-options';

/**
 * Renders actions dashboard component.
 * We can view all user actions in grid view.
 * We can go to the action statistics page.
 */
@Component({
  selector: 'app-actions-dashboard',
  templateUrl: './actions-dashboard.component.html',
  styleUrls: ['./actions-dashboard.component.scss'],
})
export class ActionsDashboardComponent implements OnInit, OnDestroy {
  actions: Actions[];
  columns: TableColumn[] = getActionsColumns();
  apiSubscription: Subscription;

  menuData: any[] = exportToExcelMenu();

  constructor(
    private router: Router,
    private apiService: ActionsApiService,
    private dataExportService: DataExportService
  ) {}

  action(value: any) {
    this.checkExportType(value);
  }

  checkExportType(exportType: number) {
    switch (exportType) {
      case 1:
        this.export();
        break;
      case 2:
        this.exportAll();
        break;
    }
  }

  /**
   * Navigate to actions statistics page.
   */
  goToStatisticCharts() {
    this.router.navigate([links.actionsStatistics.navigateTo]);
  }

  export() {
    this.dataExportService.exportTableToFile(
      'actions-dashboard',
      'Actions_Data'
    );
  }

  exportAll() {
    this.dataExportService.exportJsonToFile(this.actions, 'Actions_Data_All');
  }

  /**
   * Sort the data, based on sort parameters.
   * @param sortParameters current sort parameters.
   * @returns sorted data.
   */
  sortData(sortParameters: Sort) {
    const keyName = sortParameters.active;
    if (sortParameters.direction === 'asc') {
      this.actions = this.actions.sort((a: Actions, b: Actions) =>
        a[keyName].localeCompare(b[keyName])
      );
      return this.actions;
    } else if (sortParameters.direction === 'desc') {
      this.actions = this.actions.sort((a: Actions, b: Actions) =>
        b[keyName].localeCompare(a[keyName])
      );
      return this.actions;
    } else {
      return this.actions;
    }
  }

  /**
   * When component is initialized, the data
   * is retrieved from API and loaded into the grid.
   */
  ngOnInit(): void {
    this.apiSubscription = this.apiService
      .getActions()
      .subscribe((data) => (this.actions = data));
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    this.apiSubscription.unsubscribe();
  }
}
