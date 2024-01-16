import useClients from "../../hooks/useClients";
import DataSkeleton from "../generic/DataSkeleton";
import { ModalProps } from "@chakra-ui/react";
import Clients from "./Clients";
import { Client } from "../../services/client-service";
import GenericModal from "../generic/GenericModal";
interface Props extends ModalProps {
  onSelect: (client: Client) => void;
}
const ClientPickerModal = (props: Props) => {
  const { data, isLoading } = useClients();

  return (
    <GenericModal {...props}>
      <DataSkeleton isLoading={isLoading} data={data}>
        {data && (
          <Clients {...props} clientList={data} onSelect={props.onSelect} />
        )}
      </DataSkeleton>
    </GenericModal>
  );
};

export default ClientPickerModal;
