import {
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalProps,
} from "@chakra-ui/react";

interface Props extends ModalProps {}

const GenericModal = (props: Props) => {
  return (
    <Modal {...props} size="6xl">
      <ModalContent bg="gray.800">
        <ModalCloseButton />
        <ModalBody>{props.children}</ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default GenericModal;
