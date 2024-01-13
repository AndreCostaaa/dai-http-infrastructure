import EmployeeModal from "./EmployeeModal";
import { ModalProps } from "@chakra-ui/react";

const AssignMechanicModal = (props: ModalProps) => {
  return <EmployeeModal {...props} employeeList={[]} />;
};

export default AssignMechanicModal;
