import { SettingsComponent } from './../components/pages/settings/settings.component';
import { ActionsStatisticsComponent } from './../components/pages/actions-statistics/actions-statistics.component';
import { CreateUserComponent } from './../components/pages/users/create-user/create-user.component';
import { HomeComponent } from '../components/pages/home/home.component';
import { AppLink } from './../models/app-link.model';
import { ReadUserComponent } from '../components/pages/users/read-users/read-users.component';
import { ActionsDashboardComponent } from '../components/pages/actions-dashboard/actions-dashboard.component';
import { UserDetailsComponent } from '../components/pages/users/user-details/user-details.component';
import { PageNotFoundComponent } from '../components/pages/page-not-found/page-not-found.component';
import { LoginComponent } from '../components/pages/login/login.component';
import { UpdateUserComponent } from '../components/pages/users/update-user/update-user.component';
import { StatusDetailsComponent } from '../components/pages/status-details/status-details.component';
import { PermissionDeniedComponent } from '../components/pages/permission-denied/permission-denied.component';
import { ChatRoomComponent } from '../components/chat-room/chat-room.component';
import { NotificationsComponentComponent } from '../components/notification/notifications-component/notifications-component.component';

export const links = {
  home: new AppLink('home', 'Home', HomeComponent, 'home', '/home'),
  createUser: new AppLink(
    'create-user',
    'Create User',
    CreateUserComponent,
    'home',
    '/create-user'
  ),
  readUsers: new AppLink(
    'read-users',
    'View Users',
    ReadUserComponent,
    'group',
    '/read-users'
  ),
  actionsDashboard: new AppLink(
    'actions-dashboard',
    'Actions Dashboard',
    ActionsDashboardComponent,
    'dashboard'
  ),
  actionsStatistics: new AppLink(
    'actions-statistics',
    'Actions Statistics',
    ActionsStatisticsComponent,
    'home',
    '/actions-statistics'
  ),
  userDetails: new AppLink(
    'user-details/:id',
    'User Details',
    UserDetailsComponent,
    'home',
    '/user-details'
  ),
  pageNotFound: new AppLink(
    'page-not-found',
    'Page Not Found',
    PageNotFoundComponent,
    'home'
  ),
  updateUser: new AppLink(
    'update-user/:id',
    'Update User',
    UpdateUserComponent,
    '',
    '/update-user'
  ),
  statusDetails: new AppLink(
    'status-details',
    'Status Details',
    StatusDetailsComponent,
    'power_settings_new',
    '/status-details'
  ),
  permissionDenied: new AppLink(
    'permission-denied',
    'Permission Denied',
    PermissionDeniedComponent,
    '',
    '/permission-denied'
  ),
  chatRoom: new AppLink(
    'chat-room/:userId/:firstName/:lastName',
    'Chat Room',
    ChatRoomComponent,
    '',
    '/chat-room/'
  ),
  notifications: new AppLink(
    'notifications',
    'Nottifications',
    NotificationsComponentComponent,
    '',
    '/notifications'
  ),

  login: new AppLink('login', 'Login', LoginComponent, 'home', '/login'),
  settings: new AppLink('settings', 'Settings', SettingsComponent, 'settings'),
};
