import { Service } from "../services/service-client";
import useData from "./useData";

const useServicesByState = (stateId: number) =>
  useData<Service[]>(`/services/state/${stateId}`, undefined, [stateId]);

export default useServicesByState;
