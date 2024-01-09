import create from "./http-service";

export interface ServiceBill {
  id: number;
  grossPrice: number;
  delivered: boolean;
  paid: boolean;
  discountPercentage: number;
}
export default create("/serviceBills");
