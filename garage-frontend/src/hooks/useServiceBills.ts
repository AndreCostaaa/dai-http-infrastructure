import { ServiceBill } from "../services/service-bill";
import useData from "./useData";

const useServiceBills = () => useData<ServiceBill[]>("/serviceBills");

export default useServiceBills;
