import useMechanics from "../../hooks/useMechanics";
import DataSkeleton from "../generic/DataSkeleton";
import EmployeeModal from "./EmployeeModal";
import { ModalProps } from "@chakra-ui/react";

const AssignMechanicModal = (props: ModalProps) => {
  const { data, isLoading } = useMechanics();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <EmployeeModal {...props} employeeList={data} />}
    </DataSkeleton>
  );
};

export default AssignMechanicModal;
