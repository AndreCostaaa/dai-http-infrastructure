import {
  Modal,
  ModalContent,
  ModalCloseButton,
  ModalBody,
  ModalProps,
  VStack,
  Image,
} from "@chakra-ui/react";
interface Props extends ModalProps {
  imageList: string[];
}
const ImageModal = (props: Props) => {
  return (
    <Modal {...props} size="full">
      <ModalContent bg="gray.800">
        <ModalCloseButton />
        <ModalBody>
          <VStack>
            {props.imageList.map((url) => (
              <Image src={url}></Image>
            ))}
          </VStack>
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default ImageModal;
