import { CarPart } from "../services/car-part-service";
import useData from "./useData";

const useCarPartById = (id: number) =>
  useData<CarPart>(`/carParts/${id}`, undefined, [id]);

export default useCarPartById;
