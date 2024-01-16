import { IconButton, Td } from "@chakra-ui/react";
import { ServiceBill } from "../../services/service-bill";
import { MdSend } from "react-icons/md";
import { sendServiceBill } from "../../actions/service-bill-actions";
import { CheckIcon, CloseIcon } from "@chakra-ui/icons";
interface Props {
  serviceBill: ServiceBill;
}
const ServiceBillRow = ({ serviceBill }: Props) => {
  return (
    <>
      <Td>{serviceBill.id}</Td>
      <Td>{serviceBill.price}.-</Td>
      <Td>{serviceBill.discountPercentage}%</Td>
      <Td>
        {serviceBill.price * (1 - serviceBill.discountPercentage / 100)}.-
      </Td>
      <Td>{serviceBill.delivered ? <CheckIcon /> : <CloseIcon />}</Td>
      <Td>{serviceBill.paid ? <CheckIcon /> : <CloseIcon />}</Td>
      <Td>
        <IconButton
          onClick={() => sendServiceBill(serviceBill)}
          icon={<MdSend />}
          aria-label={""}
        ></IconButton>
      </Td>
    </>
  );
};

export default ServiceBillRow;
