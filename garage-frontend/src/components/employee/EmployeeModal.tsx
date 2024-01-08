import React from "react";
import Employees from "./Employees";
import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
interface Props extends ModalProps {
  employeeId: number;
}

const EmployeeModal = (props: Props) => {
  return (
    <GenericModal {...props}>
      <Employees employeeList={[]} />
    </GenericModal>
  );
};

export default EmployeeModal;
