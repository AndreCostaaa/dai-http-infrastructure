import { Service } from "../services/service-client";
import useData from "./useData";

const useServiceById = (id: number) =>
  useData<Service>(`/services/${id}`, undefined, [id]);

export default useServiceById;
