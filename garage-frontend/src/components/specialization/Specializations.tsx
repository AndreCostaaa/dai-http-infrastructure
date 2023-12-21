import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import { Specialization } from "../../services/specialization-service";
import SpecializationRow from "./Specialization";

interface Props {
  specializations: Specialization[];
}
const Specializations = ({ specializations }: Props) => {
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
            <SpecializationRow key={i} specialization={spc} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Specializations;
