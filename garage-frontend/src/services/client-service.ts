import { Person } from "./http-service";

export interface Client extends Person {
  email: string;
  street: string;
  streetNo: number;
  npa: number;
  country: string;
}