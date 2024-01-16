import create from "./http-service";
import { Person } from "./person-service";

export interface Client extends Person {
  email: string;
  street: string;
  streetNo: number;
  npa: number;
  country: string;
}

export default create("/clients");
