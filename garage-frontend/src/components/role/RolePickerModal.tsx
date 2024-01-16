import DataSkeleton from "../generic/DataSkeleton";
import { ModalProps } from "@chakra-ui/react";
import GenericModal from "../generic/GenericModal";
import { Role } from "../../services/role-service";
import Roles from "./Roles";
import useRoles from "../../hooks/useRoles";
interface Props extends ModalProps {
  onSelect: (role: Role) => void;
}
const RolePickerModal = (props: Props) => {
  const { data, isLoading } = useRoles();

  return (
    <GenericModal {...props}>
      <DataSkeleton isLoading={isLoading} data={data}>
        {data && <Roles {...props} roles={data} onSelect={props.onSelect} />}
      </DataSkeleton>
    </GenericModal>
  );
};

export default RolePickerModal;
