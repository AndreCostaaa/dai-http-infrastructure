import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import NewCar from "./NewCar";

const NewCarModal = (props: ModalProps) => {
  return (
    <GenericModal {...props}>
      <NewCar />
    </GenericModal>
  );
};

export default NewCarModal;
