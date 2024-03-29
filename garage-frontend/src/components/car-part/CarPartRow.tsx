import { Button, Td, Tr, useDisclosure } from "@chakra-ui/react";
import carPartService, { CarPart } from "../../services/car-part-service";
import AssignServiceModal from "../service/AssignServiceModal";
import { Service } from "../../services/service-client";
import ServiceDetailModal from "../service/ServiceDetailModal";
interface Props {
  part: CarPart;
}
const CarPartRow = ({ part }: Props) => {
  const {
    onOpen: onOpenAssign,
    isOpen: isOpenAssign,
    onClose: onCloseAssign,
  } = useDisclosure();
  const {
    onOpen: onOpenService,
    isOpen: isOpenService,
    onClose: onCloseService,
  } = useDisclosure();

  return (
    <>
      <Tr>
        <Td>{part.id}</Td>
        <Td>{part.name}</Td>
        <Td>{part.description}</Td>
        <Td>{part.supplier}</Td>
        <Td>{part.supplierRef}</Td>
        <Td>{part.buyPrice}</Td>
        <Td>{part.sellPrice}</Td>
        <Td>
          {part.serviceId == 0 ? (
            <Button onClick={onOpenAssign}>Assigner</Button>
          ) : (
            <Button onClick={onOpenService}>Details</Button>
          )}
        </Td>
      </Tr>
      {isOpenAssign && (
        <AssignServiceModal
          isOpen={isOpenAssign}
          onClose={onCloseAssign}
          children={null}
          onAssign={(service: Service) =>
            carPartService.update({ ...part, serviceId: service.id } as CarPart)
          }
        />
      )}

      {isOpenService && (
        <ServiceDetailModal
          isOpen={isOpenService}
          onClose={onCloseService}
          children={null}
          serviceId={part.serviceId}
        />
      )}
    </>
  );
};

export default CarPartRow;
