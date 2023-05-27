import { UserDetails } from './user-details.model';
import { UserRole } from './user-role.model';

export class User {
  id: number;
  firstName: string;
  middleName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  userDetails: UserDetails;
  roles: UserRole[];
}
