import { AxiosRequestConfig } from "axios";
import useData from "./useData";
import { Service } from "../services/service-client";

const useServices = () => useData<Service[]>("/services");

export default useServices;
