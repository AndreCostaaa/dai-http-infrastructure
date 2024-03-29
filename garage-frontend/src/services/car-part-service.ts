import create from "./http-service";

export interface CarPart {
  id: number;
  serviceId: number;
  supplier: string;
  supplierRef: string;
  name: string;
  description: string;
  buyPrice: number;
  sellPrice: number;
}

export default create("/carParts");
