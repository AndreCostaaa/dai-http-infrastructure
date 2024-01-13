import useData from "./useData";
import { Specialization } from "../services/specialization-service";

const useSpecializations = (id: Number) =>
  useData<Specialization>(`/specializations`);

export default useSpecializations;
