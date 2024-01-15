import { Td, Tr } from "@chakra-ui/react";
import { Role } from "../../services/role-service";
import { CheckIcon, CloseIcon } from "@chakra-ui/icons";

interface Props {
  role: Role;
}
const RoleRow = ({ role }: Props) => {
  return (
    <>
      <Td>{role.id}</Td>
      <Td>{role.name}</Td>
      <Td>{role.isMechanic ? <CheckIcon /> : <CloseIcon />}</Td>
      <Td>{role.canAssignOthers ? <CheckIcon /> : <CloseIcon />}</Td>
      <Td>{role.canCreate ? <CheckIcon /> : <CloseIcon />}</Td>
    </>
  );
};

export default RoleRow;
