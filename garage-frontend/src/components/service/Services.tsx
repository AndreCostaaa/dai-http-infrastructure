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
import { Service } from "../../services/service-client";
import ServiceRow from "./ServiceRow";
interface Props {
  serviceList: Service[];
  onSelect?: (service: Service) => void;
}
const Services = ({ serviceList, onSelect }: Props) => {
  const headers = ["id", "voiture", "client", "mecanicien", "etat", ""];

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
          {serviceList
            .sort((serviceA, serviceB) => serviceA.state.id - serviceB.state.id)
            .map((service, i) => (
              <Tr>
                <ServiceRow key={i} service={service} />
                <Td>
                  {onSelect && (
                    <Button onClick={() => onSelect(service)}>
                      Selectionner
                    </Button>
                  )}
                </Td>
              </Tr>
            ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Services;
