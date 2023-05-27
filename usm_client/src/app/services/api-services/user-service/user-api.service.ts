import { RoleRequest } from '../../../models/role-request.model';
import { UserCredentials } from '../../../models/user-credentials.model';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { RequestSearchUserByNames } from 'src/app/models/request-search-user-by-names.model';

/**
 * Service for user API requests.
 */
@Injectable({
  providedIn: 'root',
})
export class UserApiService {
  constructor(private httpClient: HttpClient) {}

  /**
   * GET METHOD
   * Get all users from the database via API request.
   * @param page the default page number (not required).
   * @param size request parameter for the size of the response body (not required).
   * @returns users list.
   */
  getUsers(page?: number, size?: number): Observable<User[]> {
    return this.httpClient.get(`${environment.userApi}users`) as Observable<
      User[]
    >;
  }

  getUsersPagination(selectedPage?: number, pageSize?: number) {
    // pagination/users/{selectedPage}/{pageSize}
    return this.httpClient.get(
      `${environment.userApi}pagination/users/${selectedPage}/${pageSize}`
    );
  }

  /**
   * GET METHOD
   * Get exactly one user from the database via API request.
   * @param userId path variable for the user to be retrieved.
   * @returns retrieved user.
   */
  getExactUsers(userId: number): Observable<User> {
    return this.httpClient.get(
      `${environment.userApi}users/${userId}`
    ) as Observable<User>;
  }

  searchUsersByNames(
    selectedPage: number,
    pageSize: number,
    requestParams: RequestSearchUserByNames
  ): Observable<User[]> {
    return this.httpClient.get(
      `${environment.userApi}pagination/search/users/${selectedPage}/${pageSize}`,
      {
        params: {
          firstName: requestParams.firstName,
          middleName: requestParams.middleName,
          lastName: requestParams.lastName,
        },
      }
    ) as Observable<User[]>;
  }

  /**
   * DELETE METHOD
   * Delete user by user id.
   * @param userId path variable for the user to be deleted.
   * @returns string confirmation.
   */
  deleteUserById(userId: number) {
    return this.httpClient.delete(`${environment.userApi}users/${userId}`, {
      responseType: 'text',
    });
  }

  /**
   * PUT METHOD
   * Update user by sending new user information to the API.
   * @param userId path variable for the user to be updated.
   * @param user request parameter for the new user information.
   * @returns user with updated information.
   */
  updateUser(userId: number, user: User): Observable<User> {
    return this.httpClient.put(
      `${environment.userApi}users/${userId}`,
      user
    ) as Observable<User>;
  }

  /**
   * POST METHOD
   * @param user new user to be sent to the API.
   * @param userCredentials new user credentials to be sent to the API.
   * @returns newly created user.
   */
  createNewUser(
    user: User,
    userCredentials: UserCredentials
  ): Observable<User> {
    const body = {
      id: user.id,
      firstName: user.firstName,
      middleName: user.middleName,
      lastName: user.lastName,
      email: user.email,
      phoneNumber: user.phoneNumber,
      userDetails: user.userDetails,
      roles: user.roles,
      userCredentials: userCredentials,
    };
    return this.httpClient.post(
      `${environment.userApi}users`,
      body
    ) as Observable<User>;
  }

  /**
   * POST METHOD
   * Assign role to existing user
   * @param roleRequest requst object containg
   * user id to be assigned to and role information
   * @returns
   */
  assignRoleToUser(roleRequest: RoleRequest): Observable<Object> {
    return this.httpClient.post(
      `${environment.userApi}assign/role/`,
      roleRequest
    ) as Observable<Object>;
  }

  /**
   * POST METHOD
   * @param userId select user id to unassign role from.
   * @param roleId select role id to unassign
   * @returns
   */
  unassignUserRole(userId: number, roleId: number) {
    return this.httpClient.post(
      `${environment.userApi}role/unassign/${userId}/${roleId}`,
      undefined,
      { responseType: 'text' }
    );
  }
}
