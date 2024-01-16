import { Client } from "../../services/client-service";
import { Td } from "@chakra-ui/react";
interface Props {
  client: Client;
}
const ClientRow = ({ client }: Props) => {
  return (
    <>
      <Td>{client.id}</Td>
      <Td>{client.firstName}</Td>
      <Td>{client.lastName}</Td>
      <Td>{client.phoneNo}</Td>
      <Td>{client.email}</Td>
      <Td>{client.street + " " + client.streetNo}</Td>
      <Td>{client.npa}</Td>
    </>
  );
};

export default ClientRow;
