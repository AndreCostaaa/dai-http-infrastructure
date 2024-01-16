import {
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
  Button,
  Td,
} from "@chakra-ui/react";
import { Client } from "../../services/client-service";
import ClientRow from "./ClientRow";

interface Props {
  clientList: Client[];
  onSelect?: (client: Client) => void;
}
const Clients = ({ clientList, onSelect }: Props) => {
  const headers = ["id", "prenom", "nom", "phone", "email", "adresse", "npa"];
  if (onSelect) {
    headers.push("");
  }
  return (
    <TableContainer>
      <Table>
        <Thead>
          <Tr>
            {headers.map((key, i) => (
              <Th key={i}>{key}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {clientList.map((client, i) => (
            <Tr>
              <ClientRow key={i} client={client} />
              <Td>
                {onSelect && (
                  <Button onClick={() => onSelect(client)}>Selectionner</Button>
                )}
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Clients;
