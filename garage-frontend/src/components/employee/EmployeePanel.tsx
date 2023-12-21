import React from "react";
import Employees from "./Employees";
import { Employee } from "../../services/employee-service";
const data: Array<Employee> = [
  {
    fname: "andre",
    id: 1,
    lname: "costa",
    phoneCode: "41",
    phoneNo: "75856412",
    roleId: 2,
    specializationId: 2,
  },
];
const EmployeePanel = () => {
  return <Employees employeeList={data} />;
};

export default EmployeePanel;
