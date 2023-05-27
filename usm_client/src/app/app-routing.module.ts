import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProtectedLayoutComponent } from './components/layouts/protected-layout/protected-layout.component';
import { PublicLayoutComponent } from './components/layouts/public-layout/public-layout.component';
import { AuthenticationGuard } from './guards/AuthenticationGuard';
import { links } from '../app/utils/link-constants';
import { PublicGuard } from './guards/PublicGuard';
import { HealthCheckComponent } from './components/pages/health-check/health-check.component';
import { PermissionsGuard } from './guards/permissions.guard';
import { Permissions } from './models/permissions.model';

const routes: Routes = [
  { path: '', redirectTo: links.home.path, pathMatch: 'full' },
  //Public Layout routes
  {
    path: '',
    component: PublicLayoutComponent,
    children: [
      //public routes go here
      {
        path: links.login.path,
        canActivate: [PublicGuard],
        component: links.login.component,
        data: { title: links.login.title },
      },
      {
        path: links.pageNotFound.path,
        component: links.pageNotFound.component,
        data: { title: links.pageNotFound.title },
      },
    ],
  },
  {
    path: '',
    component: ProtectedLayoutComponent,
    children: [
      //routes secured behind a login go here
      {
        path: links.home.path,
        canActivate: [AuthenticationGuard],
        component: links.home.component,
        data: { title: links.home.title },
      },
      {
        path: links.createUser.path,
        canActivate: [AuthenticationGuard, PermissionsGuard],
        component: links.createUser.component,
        data: {
          title: links.createUser.title,
          permission: [Permissions.admin],
        },
      },
      {
        path: links.readUsers.path,
        canActivate: [AuthenticationGuard],
        component: links.readUsers.component,
        data: { title: links.readUsers.title },
      },
      {
        path: links.actionsDashboard.path,
        canActivate: [AuthenticationGuard],
        component: links.actionsDashboard.component,
        data: { title: links.actionsDashboard.title },
      },
      {
        path: links.actionsStatistics.path,
        canActivate: [AuthenticationGuard, PermissionsGuard],
        component: links.actionsStatistics.component,
        data: {
          title: links.actionsStatistics.title,
          permission: [Permissions.admin, Permissions.adminHelper],
        },
      },
      {
        path: links.userDetails.path,
        canActivate: [AuthenticationGuard, PermissionsGuard],
        component: links.userDetails.component,
        data: {
          title: links.userDetails.title,
          permission: [Permissions.admin, Permissions.adminHelper],
        },
      },
      {
        path: links.settings.path,
        canActivate: [AuthenticationGuard],
        component: links.settings.component,
        data: { title: links.settings.title },
      },
      {
        path: links.updateUser.path,
        canActivate: [AuthenticationGuard, PermissionsGuard],
        component: links.updateUser.component,
        data: {
          title: links.updateUser.title,
          permission: [Permissions.admin, Permissions.adminHelper],
        },
      },
      {
        path: links.statusDetails.path,
        canActivate: [AuthenticationGuard, PermissionsGuard],
        component: links.statusDetails.component,
        data: {
          title: links.statusDetails.title,
          permission: [Permissions.admin, Permissions.adminHelper, Permissions.basicUser],
        },
      },
      {
        path: links.permissionDenied.path,
        canActivate: [AuthenticationGuard],
        component: links.permissionDenied.component,
        data: { title: links.permissionDenied.title },
      },
      {
        path: links.chatRoom.path,
        canActivate: [AuthenticationGuard],
        component: links.chatRoom.component,
        data: { title: links.chatRoom.title },
      },
      {
        path: links.notifications.path,
        canActivate: [AuthenticationGuard],
        component: links.notifications.component,
        data: { title: links.notifications.title },
      },
    ],
  },
  { path: '**', redirectTo: links.pageNotFound.path, pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthenticationGuard],
})
export class AppRoutingModule {}
