import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import NewClient from "./NewClient";

const NewClientModal = (props: ModalProps) => {
  return (
    <GenericModal {...props}>
      <NewClient />
    </GenericModal>
  );
};

export default NewClientModal;
