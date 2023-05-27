import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { ActionsStatisticsComponent } from './components/pages/actions-statistics/actions-statistics.component';
import { HomeComponent } from './components/pages/home/home.component';
import { ActionsDashboardComponent } from './components/pages/actions-dashboard/actions-dashboard.component';
import { LoginComponent } from './components/pages/login/login.component';
import { PageNotFoundComponent } from './components/pages/page-not-found/page-not-found.component';
import { SettingsComponent } from './components/pages/settings/settings.component';
import { UserDetailsComponent } from './components/pages/users/user-details/user-details.component';
import { PublicLayoutComponent } from './components/layouts/public-layout/public-layout.component';
import { ProtectedLayoutComponent } from './components/layouts/protected-layout/protected-layout.component';
import { CreateUserComponent } from './components/pages/users/create-user/create-user.component';
import { ReadUserComponent } from './components/pages/users/read-users/read-users.component';
import { UpdateUserComponent } from './components/pages/users/update-user/update-user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSidenavModule } from '@angular/material/sidenav';
import { TableComponent } from './components/table/table.component';
import { MatTableModule } from '@angular/material/table';
import { DataPropertyGetterPipe } from './components/pipes/data-property-getter.pipe';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { TrailingCommasPipe } from './components/pipes/trailing-commas.pipe';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { DeleteUserComponent } from './components/pages/users/delete-user/delete-user.component';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DialogComponent } from './components/dialog/dialog.component';
import { GoogleChartsModule } from 'angular-google-charts';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { DropDownSelectorComponent } from './components/drop-down-selector/drop-down-selector.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { UserRolesEditorComponent } from './components/user-roles/user-roles-editor.component';
import { DateFormatDDMMYYYYPipe } from './components/pipes/date-format-ddmmyyyy.pipe';
import { HealthCheckComponent } from './components/pages/health-check/health-check.component';
import { StatusDetailsComponent } from './components/pages/status-details/status-details.component';
import { PermissionDeniedComponent } from './components/pages/permission-denied/permission-denied.component';
import { MatMenuModule } from '@angular/material/menu';
import { MenuComponent } from './components/menu/menu.component';
import { ChatRoomComponent } from './components/chat-room/chat-room.component';
import { NotificationsComponentComponent } from './components/notification/notifications-component/notifications-component.component';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NotificationListComponent } from './components/notification/notification-list/notification-list.component';
import { NgbCollapseModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatToolbarModule} from '@angular/material/toolbar';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    ActionsStatisticsComponent,
    HomeComponent,
    ActionsDashboardComponent,
    LoginComponent,
    PageNotFoundComponent,
    SettingsComponent,
    UserDetailsComponent,
    PublicLayoutComponent,
    ProtectedLayoutComponent,
    CreateUserComponent,
    ReadUserComponent,
    UpdateUserComponent,
    TableComponent,
    DataPropertyGetterPipe,
    TrailingCommasPipe,
    DeleteUserComponent,
    DialogComponent,
    DropDownSelectorComponent,
    UserRolesEditorComponent,
    DateFormatDDMMYYYYPipe,
    HealthCheckComponent,
    StatusDetailsComponent,
    PermissionDeniedComponent,
    MenuComponent,
    ChatRoomComponent,
    NotificationsComponentComponent,
    NotificationListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSidenavModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatIconModule,
    MatButtonModule,
    MatTabsModule,
    HttpClientModule,
    MatCardModule,
    ReactiveFormsModule,
    MatDividerModule,
    MatExpansionModule,
    FormsModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    GoogleChartsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatDialogModule,
    MatTooltipModule,
    MatMenuModule,
    NgbModule,
    NgbCollapseModule,
    FlexLayoutModule,
    MatSnackBarModule,
    MatToolbarModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
  entryComponents: [UserRolesEditorComponent, DialogComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
