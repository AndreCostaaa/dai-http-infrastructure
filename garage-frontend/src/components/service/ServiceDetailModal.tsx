import { ModalProps, useToast } from "@chakra-ui/react";
import serviceClient, { Service } from "../../services/service-client";
import GenericModal from "../generic/GenericModal";
import ServiceDetails from "./ServiceDetails";
import useServiceById from "../../hooks/useServiceById";
interface Props extends ModalProps {
  serviceId: number;
  onSubmit?: (service: Service) => void;
}
const ServiceDetailModal = (props: Props) => {
  const { data, setData } = useServiceById(props.serviceId);
  const toast = useToast();
  const onSubmit = (service: Service) => {
    serviceClient
      .update(service)
      .then(({ data }) => {
        setData(data);
        if (props.onSubmit) {
          props.onSubmit(data);
        }
        toast({
          title: "Mise à jour effectuée",
          status: "success",
          duration: 2000,
          isClosable: true,
        });
      })
      .catch((error) => {
        const description =
          error.response?.data.title || "Connexion Impossible";
        toast({
          title: "Erreur",
          status: "error",
          description: description,
          duration: 5000,
          isClosable: true,
        });
      });
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
