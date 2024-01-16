import { TableContainer, Table, Thead, Tr, Th, Tbody } from "@chakra-ui/react";
import { ServiceBill } from "../../services/service-bill";
import ServiceBillRow from "./ServiceBillRow";

interface Props {
  serviceBillList: ServiceBill[];
}
const ServiceBills = ({ serviceBillList }: Props) => {
  const headers = ["id", "prix brut", "rabais", "prix net", "livré", "payé"];

  return (
    <TableContainer>
      <Table>
        <Thead>
          <Tr>
            {headers.map((key, i) => (
              <Th key={i}>{key}</Th>
            ))}
          </Tr>
        </Thead>
        <Tbody>
          {serviceBillList.map((sb, i) => (
            <Tr>
              <ServiceBillRow key={i} serviceBill={sb} />
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default ServiceBills;
