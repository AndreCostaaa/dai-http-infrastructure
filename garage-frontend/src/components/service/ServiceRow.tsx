import { Service } from "../../services/service-client";
import { Button, Td, Tr } from "@chakra-ui/react";
interface Props {
  service: Service;
}
const ServiceRow = ({ service }: Props) => {
  return (
    <Tr>
      <Td>{service.id}</Td>
      <Td>
        {service.car.model} - {service.car.brand}
      </Td>
      <Td>
        {service.client.fname} {service.client.lname}
      </Td>
      <Td>{service.mechanic?.lname || "Assigner"}</Td>
      <Td>{service.state.title}</Td>
      <Td>{service.state.title}</Td>
      <Td>{service.state.title}</Td>
      <Td>{service.state.title}</Td>
      <Td>
        <Button></Button>
      </Td>
    </Tr>
  );
};

export default ServiceRow;
