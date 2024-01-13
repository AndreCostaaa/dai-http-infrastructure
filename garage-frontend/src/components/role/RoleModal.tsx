import { ModalProps } from "@chakra-ui/react";
import Roles from "./Roles";
import GenericModal from "../generic/GenericModal";
import useRoleById from "../../hooks/useRoleById";
import DataSkeleton from "../generic/DataSkeleton";

interface Props extends ModalProps {
  roleId: number;
}
const RoleModal = (props: Props) => {
  const { data, isLoading } = useRoleById(props.roleId);

  return (
    <DataSkeleton data={data} isLoading={isLoading}>
      {data && <GenericModal {...props} children={<Roles roles={[data]} />} />}
    </DataSkeleton>
  );
};

export default RoleModal;
