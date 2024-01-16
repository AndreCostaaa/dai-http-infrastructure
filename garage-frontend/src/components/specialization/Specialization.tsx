import { Td } from "@chakra-ui/react";
import { Specialization } from "../../services/specialization-service";

interface Props {
  specialization: Specialization;
}
const SpecializationRow = ({ specialization }: Props) => {
  return (
    <>
      <Td>{specialization.id}</Td>
      <Td>{specialization.name}</Td>
      <Td>{specialization.hourlyRate}</Td>
    </>
  );
};

export default SpecializationRow;
