import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import NewService from "./NewService";

const NewServiceModal = (props: ModalProps) => {
  return (
    <GenericModal {...props}>
      <NewService />
    </GenericModal>
  );
};

export default NewServiceModal;
