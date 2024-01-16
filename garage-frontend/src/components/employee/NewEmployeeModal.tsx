import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import NewEmployee from "./NewEmployee";

const NewEmployeeModal = (props: ModalProps) => {
  return (
    <GenericModal {...props}>
      <NewEmployee />
    </GenericModal>
  );
};

export default NewEmployeeModal;
