import { Car } from "../services/car-service";
import useData from "./useData";

const useClientCars = (clientId: number) =>
  useData<Car[]>(`/cars/${clientId}`, undefined, [clientId]);

export default useClientCars;
