import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import { Service } from "../../services/service-client";
import Services from "./Services";
interface Props extends ModalProps {
  serviceList: Service[];
  onSelect?: (service: Service) => void;
}

const ServiceModal = (props: Props) => {
  return (
    <GenericModal {...props}>
      <Services
        serviceList={props.serviceList}
        onSelect={props.onSelect}
      ></Services>
    </GenericModal>
  );
};

export default ServiceModal;
