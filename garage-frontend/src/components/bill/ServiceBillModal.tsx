import useServiceBillById from "../../hooks/useServiceBillById";
import GenericModal from "../generic/GenericModal";
import { ModalProps } from "@chakra-ui/react";
import ServiceBills from "./ServiceBills";

interface Props extends ModalProps {
  serviceBillId: number;
}
const ServiceBillModal = (props: Props) => {
  const { data } = useServiceBillById(props.serviceBillId);

  return (
    <GenericModal {...props}>
      {data && <ServiceBills serviceBillList={[data]} />}
    </GenericModal>
  );
};

export default ServiceBillModal;
