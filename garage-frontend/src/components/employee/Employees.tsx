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
  onSelect?: (employee: Employee) => void;
}

const Employees = ({ employeeList, onSelect }: Props) => {
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
            {headers(Boolean(onSelect)).map((key, i) => (
              <Th key={i}>{key}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {employeeList.map((employee, i) => (
            <Tr>
              <EmployeeRow key={i} employee={employee} />
              {onSelect && (
                <Td>
                  <Button onClick={() => onSelect(employee)}>
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
