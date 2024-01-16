import { Service } from "../services/service-client";
import useData from "./useData";

const useMechanicServices = (mechanicId: number) =>
  useData<Service[]>(`/services/mechanic/${mechanicId}`, undefined, [
    mechanicId,
  ]);

export default useMechanicServices;
