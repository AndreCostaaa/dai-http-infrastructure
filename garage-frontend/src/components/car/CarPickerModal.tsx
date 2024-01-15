import React from "react";
import GenericModal from "../generic/GenericModal";
import useClientCars from "../../hooks/useClientCars";
import { ModalProps } from "@chakra-ui/react";
import Cars from "./Cars";
import { Car } from "../../services/car-service";
interface Props extends ModalProps {
  clientId: number;
  onSelect: (car: Car) => void;
}
const CarPickerModal = (props: Props) => {
  const { data } = useClientCars(props.clientId);

  return (
    <GenericModal {...props}>
      {data && <Cars carList={data} onSelect={props.onSelect}></Cars>}
    </GenericModal>
  );
};

export default CarPickerModal;
