import { ModalProps } from "@chakra-ui/react";
import Specializations from "./Specializations";
import { Specialization } from "../../services/specialization-service";
import GenericModal from "../generic/GenericModal";

interface Props extends ModalProps {}

const data: Array<Specialization> = [{ hourlyRate: 20, id: 1, name: "test" }];

const SpecializationModal = (props: Props) => {
  return (
    <GenericModal
      {...props}
      children={<Specializations specializations={data} />}
    />
  );
};

export default SpecializationModal;
