import create, { Entity } from "./http-service";

export interface Role extends Entity {
  name: string;
  canCreate: boolean;
  canAssignOthers: boolean;
  isMechanic: boolean;
}

export default create("roles");
