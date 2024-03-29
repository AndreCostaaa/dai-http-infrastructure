import { Car } from "../../services/car-service";
import { Button, Td, useDisclosure } from "@chakra-ui/react";
import { HiMiniMagnifyingGlass } from "react-icons/hi2";

import ClientModal from "../client/ClientModal";
import CarServicesModal from "../service/CarServicesModal";
interface Props {
  car: Car;
}
const CarRow = ({ car }: Props) => {
  const {
    isOpen: isOpenOwner,
    onOpen: onOpenOwner,
    onClose: onCloseOwner,
  } = useDisclosure();
  const {
    isOpen: isOpenService,
    onOpen: onOpenService,
    onClose: onCloseService,
  } = useDisclosure();
  return (
    <>
      <>
        <Td>{car.id}</Td>
        <Td>{car.brand}</Td>
        <Td>{car.model}</Td>
        <Td>{car.color}</Td>
        <Td>{car.chassisNo}</Td>
        <Td>{car.recType}</Td>
        <Td>
          <Button aria-label="owner" onClick={onOpenOwner}>
            <HiMiniMagnifyingGlass />
          </Button>
        </Td>
        <Td>
          <Button aria-label="services" onClick={onOpenService}>
            <HiMiniMagnifyingGlass />
          </Button>
        </Td>
      </>
      {isOpenOwner && (
        <ClientModal
          children={null}
          isOpen={isOpenOwner}
          onClose={onCloseOwner}
          clientId={car.ownerId}
        />
      )}
      {isOpenService && (
        <CarServicesModal
          children={null}
          isOpen={isOpenService}
          onClose={onCloseService}
          carId={car.id}
        />
      )}
    </>
  );
};

export default CarRow;
