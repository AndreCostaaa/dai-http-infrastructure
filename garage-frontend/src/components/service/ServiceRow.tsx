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
      <Tr>
        <Td>{service.id}</Td>
        <Td>
          {service.car.model} - {service.car.brand}
        </Td>
        <Td>
          {service.client.fname} {service.client.lname}
        </Td>
        <Td>
          {service.mechanic?.lname || (
            <Button onClick={onOpenAssign}>Assigner</Button>
          )}
        </Td>
        <Td>{service.state.title}</Td>
        <Td>
          <Button onClick={onOpenService}>Détails</Button>
        </Td>
      </Tr>
      <AssignMechanicModal
        isOpen={isOpenAssign}
        onClose={onCloseAssign}
        children={null}
      />
      <ServiceModal
        isOpen={isOpenService}
        onClose={onCloseService}
        children={null}
      />
    </>
  );
};

export default ServiceRow;
