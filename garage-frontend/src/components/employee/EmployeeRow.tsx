import { Button, Td, useDisclosure } from "@chakra-ui/react";
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
      <Td>{employee.id}</Td>
      <Td>{employee.firstName}</Td>
      <Td>{employee.lastName}</Td>
      <Td>{employee.phoneNo}</Td>
      <Td>
        <Button onClick={roleOnToggle}>{employee.roleId}</Button>
      </Td>
      <Td>
        {employee.specializationId && (
          <Button onClick={specializationOnToggle}>
            {employee.specializationId}
          </Button>
        )}
      </Td>

      {roleIsOpen && (
        <RoleModal
          roleId={employee.roleId}
          children={null}
          isOpen={roleIsOpen}
          onClose={roleOnClose}
        />
      )}
      {specializationIsOpen && employee.specializationId && (
        <SpecializationModal
          specializationId={employee.specializationId}
          children={null}
          isOpen={specializationIsOpen}
          onClose={specializationOnClose}
        />
      )}
    </>
  );
};

export default EmployeeRow;
