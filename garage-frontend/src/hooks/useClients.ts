import { Client } from "../services/client-service";
import useData from "./useData";

const useClients = () => useData<Client[]>("/clients");

export default useClients;
