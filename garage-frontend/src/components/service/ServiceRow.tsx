import { Service } from "../../services/service-client";
import { Button, Td, Tr, useDisclosure } from "@chakra-ui/react";
import AssignMechanicModal from "../employee/AssignMechanicModal";
import ServiceModal from "./ServiceModal";
interface Props {
  service: Service;
}
const ServiceRow = ({ service }: Props) => {
  const {
    onOpen: onOpenAssign,
    isOpen: isOpenAssign,
    onClose: onCloseAssign,
  } = useDisclosure();
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
          <Button onClick={onOpenAssign}>Assigner</Button>
        )}
      </Td>
      <Td>{service.state.title}</Td>
      <Td>
        <Button onClick={onOpenService}>Détails</Button>
      </Td>

      {isOpenAssign && (
        <AssignMechanicModal
          isOpen={isOpenAssign}
          onClose={onCloseAssign}
          children={null}
        />
      )}
    </>
  );
};

export default ServiceRow;
