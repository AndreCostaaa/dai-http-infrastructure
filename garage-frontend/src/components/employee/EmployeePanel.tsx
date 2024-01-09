import React from "react";
import Employees from "./Employees";
import { Employee } from "../../services/employee-service";
import useEmployees from "../../hooks/useEmployees";
import DataSkeleton from "../generic/DataSkeleton";
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
  const { data, isLoading } = useEmployees();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <Employees employeeList={data} />}
    </DataSkeleton>
  );
};

export default EmployeePanel;
