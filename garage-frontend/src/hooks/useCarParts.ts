import { CarPart } from "../services/car-part-service";
import useData from "./useData";

const useCarParts = () => useData<CarPart[]>("/carParts");

export default useCarParts;
