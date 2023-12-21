import {
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalProps,
} from "@chakra-ui/react";
import Roles from "./Roles";
import { Role } from "../../services/role-service";

interface Props extends ModalProps {}
const data: Role[] = [
  {
    canAssignOthers: true,
    canCreate: true,
    id: 1,
    isMechanic: true,
    name: "President",
  },
];
const RoleModal = (props: Props) => {
  return (
    <Modal {...props}>
      <ModalContent>
        <ModalCloseButton />
        <ModalBody>
          <Roles roles={data} />
        </ModalBody>
      </ModalContent>
    </Modal>
  );
};

export default RoleModal;
