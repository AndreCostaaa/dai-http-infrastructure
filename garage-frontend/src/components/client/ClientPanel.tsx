import React from "react";
import Clients from "./Clients";
import { Client } from "../../services/client-service";
const data: Client[] = [
  {
    country: "CH",
    email: "andremig.serzedel@heig-vd.ch",
    fname: "andre",
    lname: "costa",
    id: 2,
    npa: 1400,
    phoneCode: "41",
    phoneNo: "789789789",
    street: "Route de la Maladière",
    streetNo: 24,
  },
];
const ClientPanel = () => {
  return <Clients clientList={data} />;
};

export default ClientPanel;
