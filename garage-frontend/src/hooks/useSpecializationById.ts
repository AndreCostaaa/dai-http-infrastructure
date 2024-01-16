import useData from "./useData";
import { Specialization } from "../services/specialization-service";

const useSpecializationById = (id: Number) =>
  useData<Specialization>(`/specializations/${id}`);

export default useSpecializationById;
