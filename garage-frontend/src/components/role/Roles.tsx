import {
  Button,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";

import { Role } from "../../services/role-service";
import RoleRow from "./RoleRow";

interface Props {
  roles: Role[];
  onSelect?: (role: Role) => void;
}
const Roles = ({ roles, onSelect }: Props) => {
  const headers = [
    "id",
    "nom",
    "est mecanicien",
    "peut attribuer",
    "peut creer",
  ];
  if (onSelect) {
    headers.push("");
  }
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
          {roles.map((role, i) => (
            <Tr>
              <RoleRow key={i} role={role} />
              {onSelect && (
                <Td>
                  <Button onClick={() => onSelect(role)}>Selectionner</Button>
                </Td>
              )}
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Roles;
