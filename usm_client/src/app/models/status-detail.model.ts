export class StatusDetail {
  name: string;
  status: string;
  details: string[];

  constructor(name: string, status?: string, details?: string[]) {
    this.name = name;
    this.status = status || 'DOWN';
    this.details = details || ['-'];
  }
}
