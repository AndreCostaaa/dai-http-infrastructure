import {
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
  Td,
  Button,
} from "@chakra-ui/react";
import { Specialization } from "../../services/specialization-service";
import SpecializationRow from "./Specialization";

interface Props {
  specializations: Specialization[];
  onSelect?: (specialization: Specialization) => void;
}
const Specializations = ({ specializations, onSelect }: Props) => {
  const headers = ["id", "nom", "coût heure"];
  return (
    <TableContainer>
      <Table>
        <Thead>
          <Tr>
            {headers.map((header, i) => (
              <Th key={i}>{header}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {specializations.map((spc, i) => (
            <Tr>
              <SpecializationRow key={i} specialization={spc} />
              {onSelect && (
                <Td>
                  <Button onClick={() => onSelect(spc)}>Selectionner</Button>
                </Td>
              )}
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Specializations;
