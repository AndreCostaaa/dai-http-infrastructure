import { Person } from "./http-service";

export interface Client extends Person {
  email: string;
  street: string;
  street_no: number;
  npa: number;
  country: string;
}
