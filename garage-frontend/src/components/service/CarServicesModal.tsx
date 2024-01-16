import { ModalProps } from "@chakra-ui/react";
import DataSkeleton from "../generic/DataSkeleton";
import ServiceModal from "./ServiceModal";
import useCarServices from "../../hooks/useCarServices";

interface Props extends ModalProps {
  carId: number;
}
const CarServicesModal = (props: Props) => {
  const { data, isLoading } = useCarServices(props.carId);

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <ServiceModal {...props} serviceList={data} />}
    </DataSkeleton>
  );
};

export default CarServicesModal;
