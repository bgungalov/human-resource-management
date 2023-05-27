import { UserDetails } from './../models/user-details.model';
import { User } from '../models/user.model';
import { UserRole } from '../models/user-role.model';

export class UserBuilder {
  private user: User;

  constructor() {
    this.resetInstance();
  }

  setId(id: number): UserBuilder {
    this.user.id = id;
    return this;
  }

  setFirstName(firstName: string): UserBuilder {
    this.user.firstName = firstName;
    return this;
  }
  setMiddleName(middleName: string): UserBuilder {
    this.user.middleName = middleName;
    return this;
  }

  setLastName(lastName: string): UserBuilder {
    this.user.lastName = lastName;
    return this;
  }
  setEmail(email: string): UserBuilder {
    this.user.email = email;
    return this;
  }

  setPhoneNumber(phoneNumber: string): UserBuilder {
    this.user.phoneNumber = phoneNumber;
    return this;
  }
  setRoles(roles: UserRole[]): UserBuilder {
    this.user.roles = roles;
    return this;
  }
  setUserDetails(userDetails: UserDetails): UserBuilder {
    this.user.userDetails = userDetails;
    return this;
  }

  build(): User {
    return this.user;
  }

  resetInstance() {
    this.user = new User();
  }
}
