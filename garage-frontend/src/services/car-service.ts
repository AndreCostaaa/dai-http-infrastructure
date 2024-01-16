import create from "./http-service";

export interface Car {
  id: number;
  ownerId: number;
  chassisNo: string;
  recType: string;
  brand: string;
  model: string;
  color: string;
}

export default create("/cars");
