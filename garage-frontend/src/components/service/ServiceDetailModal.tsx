import { ModalProps } from "@chakra-ui/react";
import serviceClient, { Service } from "../../services/service-client";
import GenericModal from "../generic/GenericModal";
import Services from "./Services";
import ServiceDetails from "./ServiceDetails";
import useServiceById from "../../hooks/useServiceById";
interface Props extends ModalProps {
  serviceId: number;
}
const ServiceDetailModal = (props: Props) => {
  const { data, setData } = useServiceById(props.serviceId);

  const onSubmit = (service: Service) => {
    serviceClient.update(service).then(({ data }) => setData(data));
  };
  return (
    <GenericModal {...props}>
      {data && (
        <ServiceDetails
          service={data}
          setService={setData}
          onSubmit={onSubmit}
        />
      )}
    </GenericModal>
  );
};

export default ServiceDetailModal;
