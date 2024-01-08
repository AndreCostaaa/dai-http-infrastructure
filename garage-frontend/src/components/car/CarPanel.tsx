import React from "react";
import { Car } from "../../services/car-service";
import Cars from "./Cars";
const data: Car[] = [
  {
    brand: "wv",
    chassisNo: "122332323",
    color: "white",
    id: 1,
    model: "golf",
    ownerId: 2,
    recType: "232323",
  },
];
const CarPanel = () => {
  return <Cars carList={data} />;
};

export default CarPanel;
