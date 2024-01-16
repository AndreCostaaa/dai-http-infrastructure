import { ModalProps } from "@chakra-ui/react";
import useServicesByState from "../../hooks/useServicesByState";
import DataSkeleton from "../generic/DataSkeleton";
import ServiceModal from "./ServiceModal";
import { Service } from "../../services/service-client";
interface Props extends ModalProps {
  onAssign?: (service: Service) => void;
}
const AssignServiceModal = (props: Props) => {
  const { data, isLoading } = useServicesByState(2);

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && (
        <ServiceModal {...props} onSelect={props.onAssign} serviceList={data} />
      )}
    </DataSkeleton>
  );
};

export default AssignServiceModal;
