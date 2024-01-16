import {
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
  Button,
  Td,
  IconButton,
} from "@chakra-ui/react";
import { Service } from "../../services/service-client";
import ServiceRow from "./ServiceRow";
import { DeleteIcon } from "@chakra-ui/icons";
interface Props {
  serviceList: Service[];
  onSelect?: (service: Service) => void;
  onUpdate?: (service: Service) => void;
  onDelete?: (service: Service) => void;
}
const Services = ({ serviceList, onSelect, onUpdate, onDelete }: Props) => {
  const headers = ["id", "voiture", "client", "mecanicien", "etat", ""];

  if (onSelect) {
    headers.push("");
  }
  if (onDelete) {
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
            .map((service) => (
              <Tr>
                <ServiceRow
                  key={service.id}
                  service={service}
                  onUpdate={(service) => onUpdate && onUpdate(service)}
                />
                <Td>
                  {onSelect && (
                    <Button onClick={() => onSelect(service)}>
                      Selectionner
                    </Button>
                  )}
                </Td>
                {onDelete && (
                  <Td>
                    <IconButton
                      aria-label=""
                      icon={<DeleteIcon />}
                      onClick={() => onDelete(service)}
                    ></IconButton>
                  </Td>
                )}
              </Tr>
            ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Services;
