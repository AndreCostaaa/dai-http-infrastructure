import { Td, Tr } from "@chakra-ui/react";
import { Role } from "../../services/role-service";

interface Props {
  role: Role;
}
const RoleRow = ({ role }: Props) => {
  return (
    <Tr>
      <Td>{role.id}</Td>
      <Td>{role.name}</Td>
      <Td>{role.isMechanic}</Td>
      <Td>{role.canAssignOthers}</Td>
      <Td>{role.canCreate}</Td>
    </Tr>
  );
};

export default RoleRow;
