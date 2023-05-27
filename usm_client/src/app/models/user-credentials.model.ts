export class UserCredentials {
  username: string;
  password: string;
  firstTimeLogin: boolean;

  constructor(username: string, password: string, firstTimeLogin: boolean) {
    this.username = username;
    this.password = password;
    this.firstTimeLogin = firstTimeLogin;
  }
}
