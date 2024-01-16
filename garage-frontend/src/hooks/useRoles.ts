import { Role } from "../services/role-service";
import useData from "./useData";

const useRoles = () => useData<Role[]>("/roles");

export default useRoles;
