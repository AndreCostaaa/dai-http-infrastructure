import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import Clients from "./Clients";
interface Props extends ModalProps {
  clientId: number;
}

const ClientModal = (props: Props) => {
  return (
    <GenericModal {...props}>
      <Clients clientList={[]} />
    </GenericModal>
  );
};

export default ClientModal;
