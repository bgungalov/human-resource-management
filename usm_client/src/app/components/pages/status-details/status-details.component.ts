import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { StatusDetail } from 'src/app/models/status-detail.model';
import { TableColumn } from 'src/app/models/table-column.model';
import { HealthApiService } from 'src/app/services/api-services/health-service/health-api.service';
import { getStatusHealthDetails } from 'src/app/utils/table-columns';

@Component({
  selector: 'app-status-details',
  templateUrl: './status-details.component.html',
  styleUrls: ['./status-details.component.scss'],
})
export class StatusDetailsComponent implements OnInit {
  apiSubscription: Subscription;
  statusData: StatusDetail[];
  dataColumns: TableColumn[] = getStatusHealthDetails();

  serverTitle: string = 'Server';
  databaseTitle: string = 'Database';
  serverDiskSpaceTitle: string = 'Disk Space';

  constructor(private apiService: HealthApiService) {}

  /**
   * It takes a JSON object and creates an array of StatusDetail objects
   * @param [data] - The data returned from the API call.
   */
  private prepareStatusData(data?) {
    this.statusData = [];

    if (data) {
      this.statusData.push(new StatusDetail(this.serverTitle, data.status));
      this.statusData.push(
        new StatusDetail(this.databaseTitle, data.components.db.status, [
          data.components.db.details.database,
        ])
      );
      this.statusData.push(
        new StatusDetail(
          this.serverDiskSpaceTitle,
          data.components.diskSpace.status,
          [
            `Total = ${data.components.diskSpace.details.total}`,
            ` Free Space = ${data.components.diskSpace.details.free}`,
            ` Threshold = ${data.components.diskSpace.details.threshold}`,
          ]
        )
      );
    } else {
      this.statusData.push(new StatusDetail(this.serverTitle));
      this.statusData.push(new StatusDetail(this.databaseTitle));
      this.statusData.push(new StatusDetail(this.serverDiskSpaceTitle));
    }
  }

  /**
   * We subscribe to the API service's getHealthCheck() function, which returns an observable. If the
   * observable returns data, we call the prepareStatusData() function with the data. If the observable
   * returns an error, we call the prepareStatusData() function without any data
   */
  ngOnInit(): void {
    this.apiSubscription = this.apiService.getHealthCheck().subscribe({
      next: (data) => {
        this.prepareStatusData(data);
      },
      error: (err) => {
        this.prepareStatusData();
      },
    });
  }
}
