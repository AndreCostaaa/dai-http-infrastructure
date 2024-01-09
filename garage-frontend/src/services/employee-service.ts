import create, { Person } from "./http-service";

export interface Employee extends Person {
  roleId: number;
  specializationId: number;
}

export default create("/employees");
