import apiClient from "../services/api-client";
import { ServiceBill } from "../services/service-bill";

export function sendServiceBill(serviceBill: ServiceBill) {
  apiClient.post(`/serviceBills/send/${serviceBill.id}`);
}
