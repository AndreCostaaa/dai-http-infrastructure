import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import Clients from "./Clients";
import useClientById from "../../hooks/useClientById";
import DataSkeleton from "../generic/DataSkeleton";
interface Props extends ModalProps {
  clientId: number;
}

const ClientModal = (props: Props) => {
  const { data, isLoading } = useClientById(props.clientId);
  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      <GenericModal {...props}>
        {data && <Clients clientList={[data]} />}
      </GenericModal>
    </DataSkeleton>
  );
};

export default ClientModal;
