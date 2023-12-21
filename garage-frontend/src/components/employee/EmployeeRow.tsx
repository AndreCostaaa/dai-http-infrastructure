import { Button, Td, Tr, useDisclosure } from "@chakra-ui/react";
import { Employee } from "../../services/employee-service";
import RoleModal from "../role/RoleModal";
import SpecializationModal from "../specialization/SpecializationModal";
interface Props {
  employee: Employee;
}
const EmployeeRow = ({ employee }: Props) => {
  const {
    isOpen: roleIsOpen,
    onOpen: roleOnToggle,
    onClose: roleOnClose,
  } = useDisclosure();
  const {
    isOpen: specializationIsOpen,
    onOpen: specializationOnToggle,
    onClose: specializationOnClose,
  } = useDisclosure();
  return (
    <>
      <Tr>
        <Td>{employee.id}</Td>
        <Td>{employee.fname}</Td>
        <Td>{employee.lname}</Td>
        <Td>{employee.phoneCode + " " + employee.phoneNo}</Td>
        <Td>
          <Button onClick={roleOnToggle}>{employee.roleId}</Button>
        </Td>
        <Td>
          <Button onClick={specializationOnToggle}>
            {employee.specializationId}
          </Button>
        </Td>
      </Tr>
      <RoleModal
        roleId={employee.roleId}
        children={null}
        isOpen={roleIsOpen}
        onClose={roleOnClose}
      />
      <SpecializationModal
        specializationId={employee.specializationId}
        children={null}
        isOpen={specializationIsOpen}
        onClose={specializationOnClose}
      />
    </>
  );
};

export default EmployeeRow;
