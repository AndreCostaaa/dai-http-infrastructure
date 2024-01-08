import { Td, Tr } from "@chakra-ui/react";
interface Props {
  part: CarPart;
}
const CarPartRow = ({ part }: Props) => {
  return (
    <>
      <Tr>
        <Td>{part.id}</Td>
        <Td>{part.name}</Td>
        <Td>{part.description}</Td>
        <Td>{part.supplier}</Td>
        <Td>{part.supplierRef}</Td>
        <Td>{part.buyPrice}</Td>
        <Td>{part.sellPrice}</Td>
        <Td>{part.serviceId == null ? "Assigner" : "Details"}</Td>
      </Tr>
    </>
  );
};

export default CarPartRow;
