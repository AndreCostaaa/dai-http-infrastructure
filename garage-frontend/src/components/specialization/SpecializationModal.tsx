import { ModalProps } from "@chakra-ui/react";
import Specializations from "./Specializations";
import GenericModal from "../generic/GenericModal";
import useSpecializationById from "../../hooks/useSpecializationById";
import DataSkeleton from "../generic/DataSkeleton";

interface Props extends ModalProps {
  specializationId: number;
}

const SpecializationModal = (props: Props) => {
  const { data, isLoading } = useSpecializationById(props.specializationId);
  return (
    <DataSkeleton
      isLoading={isLoading}
      data={data}
      children={
        <GenericModal
          {...props}
          children={data && <Specializations specializations={[data]} />}
        />
      }
    ></DataSkeleton>
  );
};

export default SpecializationModal;
