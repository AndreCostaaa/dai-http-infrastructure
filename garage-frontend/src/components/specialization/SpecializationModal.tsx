import {
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalProps,
} from "@chakra-ui/react";
import Specializations from "./Specializations";
import { Specialization } from "../../services/specialization-service";

interface Props extends ModalProps {}

const data: Array<Specialization> = [{ hourlyRate: 20, id: 1, name: "test" }];

const SpecializationModal = (props: Props) => {
  return (
    <Modal {...props}>
      <ModalContent>
        <ModalCloseButton />
        <ModalBody>
          <Specializations specializations={data} />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default SpecializationModal;
