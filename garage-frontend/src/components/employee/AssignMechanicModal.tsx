import useMechanics from "../../hooks/useMechanics";
import { Employee } from "../../services/employee-service";
import { ModalProps } from "@chakra-ui/react";
import Employees from "./Employees";
import GenericModal from "../generic/GenericModal";

interface Props extends ModalProps {
  onSelect: (mechanic: Employee) => void;
}
const AssignMechanicModal = (props: Props) => {
  const { data } = useMechanics();

  return (
    <GenericModal {...props}>
      {data && <Employees employeeList={data} onSelect={props.onSelect} />}
    </GenericModal>
  );
};

export default AssignMechanicModal;
