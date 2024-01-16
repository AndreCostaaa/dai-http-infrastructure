import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import NewCarPart from "./NewCarPart";

const NewCarPartModal = (props: ModalProps) => {
  return (
    <GenericModal {...props}>
      <NewCarPart />
    </GenericModal>
  );
};

export default NewCarPartModal;
