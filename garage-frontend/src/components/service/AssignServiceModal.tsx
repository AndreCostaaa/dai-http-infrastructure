import { ModalProps } from "@chakra-ui/react";
import useServicesByState from "../../hooks/useServicesByState";
import DataSkeleton from "../generic/DataSkeleton";
import ServiceModal from "./ServiceModal";

const AssignServiceModal = (props: ModalProps) => {
  const { data, isLoading } = useServicesByState(2);

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <ServiceModal {...props} serviceList={data} />}
    </DataSkeleton>
  );
};

export default AssignServiceModal;
