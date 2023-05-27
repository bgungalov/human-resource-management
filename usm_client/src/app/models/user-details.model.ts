export class UserDetails {
  ucn: string;
  address: string;
  country: string;
  city: string;
  cityPostCode: string;
  street: string;

  constructor(
    ucn: string,
    address?: string,
    country?: string,
    city?: string,
    cityPostCode?: string,
    street?: string
  ) {
    this.ucn = ucn;
    this.address = address;
    this.country = country;
    this.city = city;
    this.cityPostCode = cityPostCode;
    this.street = street;
  }
}
