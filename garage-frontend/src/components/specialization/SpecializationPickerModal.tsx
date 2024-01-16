import { ModalProps } from "@chakra-ui/react";
import useSpecializations from "../../hooks/useSpecializations";
import Specializations from "./Specializations";
import GenericModal from "../generic/GenericModal";
import { Specialization } from "../../services/specialization-service";
interface Props extends ModalProps {
  onSelect: (specialization: Specialization) => void;
}
const SpecializationPickerModal = (props: Props) => {
  const { data } = useSpecializations();
  return (
    <GenericModal {...props}>
      {data && (
        <Specializations specializations={data} onSelect={props.onSelect} />
      )}
    </GenericModal>
  );
};

export default SpecializationPickerModal;
