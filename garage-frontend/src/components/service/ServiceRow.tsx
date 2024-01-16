import { Service } from "../../services/service-client";
import { Button, Td, useDisclosure } from "@chakra-ui/react";
import ServiceDetailModal from "./ServiceDetailModal";
interface Props {
  service: Service;
  onUpdate: (service: Service) => void;
}
const ServiceRow = ({ service, onUpdate }: Props) => {
  const {
    onOpen: onOpenService,
    isOpen: isOpenService,
    onClose: onCloseService,
  } = useDisclosure();

  return (
    <>
      <Td>{service.id}</Td>
      <Td>
        {service.car.model} - {service.car.brand}
      </Td>
      <Td>
        {service.client.firstName} {service.client.lastName}
      </Td>
      <Td>
        {service.mechanic?.lastName || (
          <Button onClick={onOpenService}>Assigner</Button>
        )}
      </Td>
      <Td>{service.state.title}</Td>
      <Td>
        <Button onClick={onOpenService}>Détails</Button>
      </Td>

      {isOpenService && (
        <ServiceDetailModal
          isOpen={isOpenService}
          onClose={onCloseService}
          onSubmit={onUpdate}
          children={null}
          serviceId={service.id}
        />
      )}
    </>
  );
};

export default ServiceRow;
