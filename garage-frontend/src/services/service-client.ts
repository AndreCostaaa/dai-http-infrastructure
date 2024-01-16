import { Car } from "./car-service";
import { Client } from "./client-service";
import { Employee } from "./employee-service";
import create from "./http-service";

export interface ServiceState {
  id: number;
  title: string;
  description: string;
}
export interface Service {
  id: number;
  mechanic: Employee | null;
  client: Client;
  car: Car;
  hoursWorked: number;
  comments: string;
  hasPictures: boolean;
  state: ServiceState;
  nextState: ServiceState;
  dateCreated: Date;
  dateCarArrival: Date | null;
  dateCarProcessing: Date | null;
  dateCarDone: Date | null;
  dateCarLeft: Date | null;
}

export default create("/services");
