import { Car } from "../../services/car-service";
import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import CarRow from "./CarRow";
interface Props {
  carList: Car[];
}
const Cars = ({ carList }: Props) => {
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
            <CarRow key={i} car={car} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default Cars;
