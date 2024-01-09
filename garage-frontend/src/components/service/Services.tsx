import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import { Service } from "../../services/service-client";
import ServiceRow from "./ServiceRow";
interface Props {
  serviceList: Service[];
}
const Services = ({ serviceList }: Props) => {
  const headers = ["id", "voiture", "client", "mecanicien", "etat", ""];
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
          {serviceList.map((service, i) => (
            <ServiceRow key={i} service={service} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Services;
