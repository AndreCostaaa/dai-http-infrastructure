import useData from "./useData";

const useCarServices = (carId: number) =>
  useData(`/services/car/${carId}`, undefined, [carId]);

export default useCarServices;
