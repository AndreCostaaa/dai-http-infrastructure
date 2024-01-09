import { ServiceBill } from "../services/service-bill";
import useData from "./useData";

const useServiceBillById = (id: number) =>
  useData<ServiceBill[]>(`/serviceBills/${id}`, undefined, [id]);

export default useServiceBillById;
