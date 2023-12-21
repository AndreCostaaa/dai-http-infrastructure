import { Td, Tr } from "@chakra-ui/react";
import { Specialization } from "../../services/specialization-service";

interface Props {
  specialization: Specialization;
}
const SpecializationRow = ({ specialization }: Props) => {
  return (
    <Tr>
      <Td>{specialization.id}</Td>
      <Td>{specialization.name}</Td>
      <Td>{specialization.hourlyRate}</Td>
    </Tr>
  );
};

export default SpecializationRow;
