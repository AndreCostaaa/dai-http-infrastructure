import Employees from "./Employees";
import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import { Employee } from "../../services/employee-service";
interface Props extends ModalProps {
  employeeList: Employee[];
}

const EmployeeModal = (props: Props) => {
  return (
    <GenericModal {...props}>
      <Employees employeeList={props.employeeList} />
    </GenericModal>
  );
};

export default EmployeeModal;
