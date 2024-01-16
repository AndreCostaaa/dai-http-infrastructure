import { Service } from "../services/service-client";
import useData from "./useData";

const useCarServices = (carId: number) =>
  useData<Service[]>(`/services/car/${carId}`, undefined, [carId]);

export default useCarServices;
