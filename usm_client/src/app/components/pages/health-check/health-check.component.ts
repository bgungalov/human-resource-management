import { AfterViewInit, Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, Observable, interval } from 'rxjs';
import { HealthApiService } from 'src/app/services/api-services/health-service/health-api.service';
import { unsubscribeFrom } from 'src/app/utils/subscription-handler';
import { TokenStorageService } from 'src/app/services/storage/token-storage.service';

/**
 * Renders the health check component.
 */
@Component({
  selector: 'app-health-check',
  templateUrl: './health-check.component.html',
  styleUrls: ['./health-check.component.scss'],
})
export class HealthCheckComponent implements OnInit, AfterViewInit, OnDestroy {
  apiSubscription: Subscription;
  intervalSubscription: Subscription;

  statusData: boolean;
  databaseStatus: string = 'DOWN';
  diskSpaceStatus: string = 'DOWN';

  decodedToken = this.tokenStorage.decodeToken();
  tokenUserCurrentRoles = this.decodedToken.user.userRoles[0].authority;

  constructor(
    private apiService: HealthApiService,
    private tokenStorage: TokenStorageService
  ) {}

  /**
   * If the status is UP, then set the statusData to true. If the database status is UP, then set the
   * databaseStatus to UP. If the diskSpace status is UP, then set the diskSpaceStatus to UP
   * @param data - The data returned from the server.
   */
  getStatusData(data) {
    if (data.status === 'UP') {
      this.statusData = true;
    }

    if (data.components.db.status && data.components.db.status === 'UP') {
      this.databaseStatus = 'UP';
    }

    if (
      data.components.diskSpace.status &&
      data.components.diskSpace.status === 'UP'
    ) {
      this.diskSpaceStatus = 'UP';
    }
  }

  /**
   * The function checks the user's role and then calls the appropriate API endpoint
   */
  ngOnInit(): void {
    this.decodedToken;

    if (
      this.tokenUserCurrentRoles == 'ROLE_ADMIN' ||
      this.tokenUserCurrentRoles == 'ROLE_ADMIN_HELPER'
    ) {
      this.apiSubscription = this.apiService.getHealthCheck().subscribe({
        next: (data) => {
          this.getStatusData(data);
        },
        error: (err) => {
          this.statusData = false;
        },
      });
    } else if (this.tokenUserCurrentRoles == 'ROLE_BASIC_USER') {
      this.apiSubscription = this.apiService.getHealthCheckCustom().subscribe({
        next: (data) => {
          this.getStatusData(data);
        },
        error: (err) => {
          this.statusData = false;
        },
      });
    }
  }

  /**
   * The function is called after the view is initialized. It subscribes to an interval of 5 seconds
   * and calls the ngOnInit() function every 5 seconds
   */
  ngAfterViewInit(): void {
    this.intervalSubscription = interval(5000).subscribe(() => {
      this.ngOnInit();
    });
  }

  /**
   * It unsubscribes from the apiSubscription and intervalSubscription.
   */
  ngOnDestroy(): void {
    unsubscribeFrom(this.apiSubscription);
    unsubscribeFrom(this.intervalSubscription);
  }
}
