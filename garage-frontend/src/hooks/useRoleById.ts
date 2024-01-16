import { Role } from "../services/role-service";
import useData from "./useData";

const useRoleById = (id: number) =>
  useData<Role>(`/roles/${id}`, undefined, [id]);

export default useRoleById;
