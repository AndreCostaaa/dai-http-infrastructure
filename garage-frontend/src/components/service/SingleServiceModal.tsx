import { ModalProps } from "@chakra-ui/react";
import ServiceModal from "./ServiceModal";
import DataSkeleton from "../generic/DataSkeleton";
import useServiceById from "../../hooks/useServiceById";

interface Props extends ModalProps {
  serviceId: number;
}
const SingleServiceModal = (props: Props) => {
  const { data, isLoading } = useServiceById(props.serviceId);

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <ServiceModal {...props} serviceList={[data]} />}
    </DataSkeleton>
  );
};

export default SingleServiceModal;
