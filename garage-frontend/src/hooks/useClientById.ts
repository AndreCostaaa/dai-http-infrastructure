import { Client } from "../services/client-service";
import useData from "./useData";

const useClientById = (id: number) => useData<Client>(`/clients/${id}`);

export default useClientById;
