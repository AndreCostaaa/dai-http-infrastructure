import { Car } from "./car-service";
import { Client } from "./client-service";
import { Employee } from "./employee-service";

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
  dateCreated: Date;
  dateCarArrival: Date;
  dateCarProcessing: Date;
  dateCarDone: Date;
  dateCarLeft: Date;
}
