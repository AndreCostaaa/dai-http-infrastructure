import { Car } from "../services/car-service";
import useData from "./useData";

const useCars = () => useData<Car[]>("/cars");

export default useCars;
