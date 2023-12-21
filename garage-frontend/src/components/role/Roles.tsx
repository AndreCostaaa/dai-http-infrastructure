import { Table, TableContainer, Tbody, Th, Thead, Tr } from "@chakra-ui/react";

import { Role } from "../../services/role-service";
import RoleRow from "./RoleRow";

interface Props {
  roles: Role[];
}
const Roles = ({ roles }: Props) => {
  const headers = [
    "id",
    "nom",
    "est mecanicien",
    "peut attribuer",
    "peut creer",
  ];
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
            <RoleRow key={i} role={role} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Roles;
