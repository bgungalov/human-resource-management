export interface RequestSearchUserByNames {
  firstName: string;
  lastName: string;
  middleName: string;
  sortOrder?: string;
  field?: string;
}
