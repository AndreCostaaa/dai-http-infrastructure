import { Client } from "../../services/client-service";
import { Td, Tr } from "@chakra-ui/react";
interface Props {
  client: Client;
}
const ClientRow = ({ client }: Props) => {
  return (
    <Tr>
      <Td>{client.id}</Td>
      <Td>{client.firstName}</Td>
      <Td>{client.lastName}</Td>
      <Td>{client.phoneNo}</Td>
      <Td>{client.email}</Td>
      <Td>{client.street + " " + client.streetNo}</Td>
      <Td>{client.npa}</Td>
    </Tr>
  );
};

export default ClientRow;
