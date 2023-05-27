import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { ChartType } from 'angular-google-charts';
import { VisitStatisticsService } from 'src/app/services/api-services/visit-statistics-service/visit-statistics.service';
import { Subscription } from 'rxjs';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { FormControl, FormGroup } from '@angular/forms';
import { CalendarService } from 'src/app/services/calendar/calendar.service';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Renders actions statistics component.
 * We can select from different type charts.
 * We can get data based on date range and method type.
 */
@Component({
  selector: 'app-actions-statistics',
  templateUrl: './actions-statistics.component.html',
  styleUrls: ['./actions-statistics.component.scss'],
})
export class ActionsStatisticsComponent implements OnInit, OnDestroy {
  chartTypes = ChartType;
  apiSubscription: Subscription;

  height = 500;
  width = 1000;

  myData = [];
  methodType: string;

  methods = [
    { value: 'GET', viewValue: 'GET' },
    { value: 'POST', viewValue: 'POST' },
    { value: 'DELETE', viewValue: 'DELETE' },
    { value: 'PUT', viewValue: 'PUT' },
  ];

  selectorDefaultValue = this.methods[0].value;

  chartOptions = {
    is3D: true,
  };

  range = new FormGroup({
    start: new FormControl<Date | null>(this.calendar.getPreviousDay()),
    end: new FormControl<Date | null>(this.calendar.getToday()),
  });

  constructor(
    private apiService: VisitStatisticsService,
    private calendar: CalendarService,
    private snackBar: MatSnackBar
  ) {}

  /**
   * Select the method type
   * @param event current event value
   */
  onSelectionChange(event) {
    this.methodType = event.value;
  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000,
    });
  }

  /**
   * Get the data from API based on the provided request information.
   * Then load the data from the API into the charts.
   */
  apiDataFetch() {
    if (this.methodType === undefined) {
      this.methodType = this.selectorDefaultValue;
    }
    this.apiSubscription = this.apiService
      .getStatistics(
        this.methodType,
        this.range.get('start').value,
        this.range.get('end').value
      )
      .subscribe({
        next: (data) => {
          this.myData = data.map((data) => [data.queryName, data.count]);
          if (this.myData && this.myData.length <= 0) {
            this.openSnackBar('No data to show', 'Close');
          }
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  /**
   * When component is initialized, the data
   * is retrieved from the API and loaded into charts.
   */
  ngOnInit(): void {
    this.apiDataFetch();
  }

  /**
   * Runs after the component is destroyed.
   * Passing all subsctiptions that need to be unsubscribed from.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);
  }
}
