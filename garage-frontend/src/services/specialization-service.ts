import { Entity } from "./http-service";

export interface Specialization extends Entity {
  name: string;
  hourlyRate: number;
}
