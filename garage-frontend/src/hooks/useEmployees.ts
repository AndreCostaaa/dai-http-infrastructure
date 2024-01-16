import { Employee } from "../services/employee-service";
import useData from "./useData";

const useEmployees = () => useData<Employee[]>("/employees");

export default useEmployees;
