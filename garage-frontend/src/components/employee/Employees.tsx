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
import { Employee } from "../../services/employee-service";
import EmployeeRow from "./EmployeeRow";
interface Props {
  employeeList: Employee[];
  onClick?: (employee: Employee) => void;
}

const Employees = ({ employeeList, onClick }: Props) => {
  const headers = (onClick: boolean) => {
    let headers = [
      "id",
      "prenom",
      "nom",
      "phone",
      "role id",
      "specialization id",
    ];
    if (onClick) {
      headers.push("selectionner");
    }
    return headers;
  };
  return (
    <TableContainer>
      <Table>
        <Thead>
          <Tr>
            {headers(Boolean(onClick)).map((key, i) => (
              <Th key={i}>{key}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {employeeList.map((employee, i) => (
            <Tr>
              <EmployeeRow key={i} employee={employee} />
              {onClick && (
                <Td>
                  <Button onClick={() => onClick(employee)}>
                    Selectionner
                  </Button>
                </Td>
              )}
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Employees;
