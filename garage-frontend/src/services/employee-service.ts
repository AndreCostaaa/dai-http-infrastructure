import create from "./http-service";
import { Person } from "./person-service";

export interface Employee extends Person {
  roleId: number;
  specializationId: number | null;
}

export default create("/employees");
