import useData from "./useData";
import { Specialization } from "../services/specialization-service";

const useSpecializations = () => useData<Specialization[]>(`/specializations`);

export default useSpecializations;
