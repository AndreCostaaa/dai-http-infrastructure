import { Entity } from "./http-service";

export interface Employee extends Entity {
  fname: string;
  lname: string;
  phoneCode: string;
  phoneNo: string;
  roleId: number;
  specializationId: number;
}
