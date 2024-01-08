import { ModalProps } from "@chakra-ui/react";
import React from "react";
import GenericModal from "../generic/GenericModal";
interface Props extends ModalProps {
  carId?: number;
  mechanicId?: number;
}

const ServiceModal = (props: Props) => {
  return <GenericModal {...props}>Hello</GenericModal>;
};

export default ServiceModal;
