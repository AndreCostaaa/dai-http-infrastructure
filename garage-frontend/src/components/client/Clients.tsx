import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import { Client } from "../../services/client-service";
import ClientRow from "./ClientRow";

interface Props {
  clientList: Client[];
}
const Clients = ({ clientList }: Props) => {
  const headers = ["id", "prenom", "nom", "phone", "email", "adresse", "npa"];
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
            <ClientRow key={i} client={client} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Clients;
