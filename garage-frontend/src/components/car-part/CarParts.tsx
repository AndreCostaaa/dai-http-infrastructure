import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import CarPartRow from "./CarPartRow";
interface Props {
  carPartList: CarPart[];
}
const CarParts = ({ carPartList }: Props) => {
  const headers = [
    "id",
    "nom",
    "description",
    "fournisseur",
    "ref fournisseur",
    "prix achat",
    "prix vente",
    "état",
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
          {carPartList.map((part, i) => (
            <CarPartRow key={i} part={part} />
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default CarParts;
