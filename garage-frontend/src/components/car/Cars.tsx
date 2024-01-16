import { Car } from "../../services/car-service";
import {
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
  Button,
  Td,
} from "@chakra-ui/react";
import CarRow from "./CarRow";
interface Props {
  carList: Car[];
  onSelect?: (car: Car) => void;
}
const Cars = ({ carList, onSelect }: Props) => {
  const headers = [
    "id",
    "brand",
    "model",
    "color",
    "no chassis",
    "rec type",
    "propriètaire",
    "services",
  ];
  if (onSelect) {
    headers.push("");
  }
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
          {carList.map((car, i) => (
            <Tr>
              <CarRow key={i} car={car} />
              <Td>
                {onSelect && (
                  <Button onClick={() => onSelect(car)}>Selectioner</Button>
                )}
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Cars;
