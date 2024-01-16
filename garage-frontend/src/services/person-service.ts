import { Entity } from "./http-service";

export interface Person extends Entity {
  firstName: string;
  lastName: string;
  phoneNo: string;
}
