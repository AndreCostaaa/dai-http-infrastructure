import Employees from "./Employees";
import useEmployees from "../../hooks/useEmployees";
import DataSkeleton from "../generic/DataSkeleton";

const EmployeePanel = () => {
  const { data, isLoading } = useEmployees();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <Employees employeeList={data} />}
    </DataSkeleton>
  );
};

export default EmployeePanel;
