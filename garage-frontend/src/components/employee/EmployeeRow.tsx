import { Td, Tr } from "@chakra-ui/react";
import { Employee } from "../../services/employee-service";
interface Props {
  employee: Employee;
}
const EmployeeRow = ({ employee }: Props) => {
  return (
    <Tr>
      <Td></Td>
    </Tr>
  );
};

export default EmployeeRow;
