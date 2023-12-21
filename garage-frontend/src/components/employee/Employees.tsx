import { Table, TableContainer, Tbody, Th, Thead, Tr } from "@chakra-ui/react";
import { Employee } from "../../services/employee-service";
import EmployeeRow from "./EmployeeRow";
interface Props {
  employeeList: Employee[];
}

const Employees = ({ employeeList }: Props) => {
  const headers = [
    "id",
    "prenom",
    "nom",
    "phone",
    "role id",
    "specialization id",
  ];
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
          {employeeList.map((employee, i) => (
            <EmployeeRow key={i} employee={employee} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Employees;
