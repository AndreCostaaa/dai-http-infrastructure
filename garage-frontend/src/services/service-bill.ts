import create from "./http-service";

export interface ServiceBill {
  id: number;
  price: number;
  delivered: boolean;
  paid: boolean;
  discountPercentage: number;
}
export default create("/serviceBills");
