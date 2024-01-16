import { Car } from "../services/car-service";
import useData from "./useData";

const useCarById = (carId: number) =>
  useData<Car>(`/cars/${carId}`, undefined, [carId]);

export default useCarById;
