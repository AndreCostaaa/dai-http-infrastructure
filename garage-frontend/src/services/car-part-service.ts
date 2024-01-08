interface CarPart {
  id: number;
  serviceId: number | null;
  supplier: string;
  supplierRef: string;
  name: string;
  description: string;
  buyPrice: number;
  sellPrice: number;
}
