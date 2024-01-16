import Employees from "./Employees";
import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import { Employee } from "../../services/employee-service";
interface Props extends ModalProps {
  employeeList: Employee[];
  onClick?: (employee: Employee) => void;
}

const EmployeeModal = (props: Props) => {
  return (
    <GenericModal {...props}>
      <Employees onSelect={props.onClick} employeeList={props.employeeList} />
    </GenericModal>
  );
};

export default EmployeeModal;
