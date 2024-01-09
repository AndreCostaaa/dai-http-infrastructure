import { Employee } from "../services/employee-service";
import useData from "./useData";

const useMechanics = () => useData<Employee[]>("/mechanics");

export default useMechanics;
