/* Defining the interface for the Actions object. */
export interface Actions {
  id: number;
  user: string;
  ip: string;
  method: string;
  url: string;
  page: string;
  queryString: string;
  refererPage: string;
  userAgent: string;
  loggedTime: string;
  uniqueVisit: string;
}
