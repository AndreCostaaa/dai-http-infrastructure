import {
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalProps,
} from "@chakra-ui/react";
import Roles from "./Roles";
import { Role } from "../../services/role-service";
import GenericModal from "../generic/GenericModal";

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
  return <GenericModal {...props} children={<Roles roles={data} />} />;
};

export default RoleModal;
