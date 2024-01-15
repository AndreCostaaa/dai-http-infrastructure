import { Service } from "../../services/service-client";
import {
  Box,
  Button,
  Heading,
  NumberDecrementStepper,
  NumberIncrementStepper,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  VStack,
  useDisclosure,
} from "@chakra-ui/react";
import useCustomScrollbar from "../../hooks/useCustomScrollbar";
import { Client } from "../../services/client-service";
import Cars from "../car/Cars";
import Clients from "../client/Clients";
import AssignMechanicModal from "../employee/AssignMechanicModal";
import Employees from "../employee/Employees";
import EditableField from "../generic/EditableField";
interface Props {
  service: Service;
  setService: (service: Service) => void;
  onSubmit: (service: Service) => void;
}
const ServiceDetails = ({ service, setService, onSubmit }: Props) => {
  const {
    onOpen: onOpenAssign,
    isOpen: isOpenAssign,
    onClose: onCloseAssign,
  } = useDisclosure();

  return (
    <VStack
      spacing={10}
      width="100%"
      overflowY="auto"
      sx={useCustomScrollbar()}
    >
      <Box width={"100%"}>
        <Heading size="lg" textAlign={"center"}>
          Client
        </Heading>
        <Clients clientList={[service.client] as Client[]} />
      </Box>

      <Box width={"100%"}>
        <Heading size="lg" textAlign={"center"}>
          Mecanicien
        </Heading>
        {service.mechanic ? (
          <Employees employeeList={[service.mechanic]} />
        ) : (
          <>
            <Button onClick={onOpenAssign}>Assigner</Button>
            <AssignMechanicModal
              isOpen={isOpenAssign}
              onClose={onCloseAssign}
              children={null}
            />
          </>
        )}
      </Box>

      <Box width={"100%"}>
        <Heading size="lg" textAlign="center">
          Voiture
        </Heading>
        <Cars carList={[service.car]} />
      </Box>

      <Box>
        <Heading size="lg">Heures</Heading>
        <NumberInput
          maxW="100px"
          mr="2rem"
          value={service.hoursWorked}
          onChange={(_, value) =>
            setService({ ...service, hoursWorked: value })
          }
        >
          <NumberInputField />
          <NumberInputStepper>
            <NumberIncrementStepper />
            <NumberDecrementStepper />
          </NumberInputStepper>
        </NumberInput>
      </Box>
      <Box>
        <Heading size="lg">Commentaires</Heading>
        <EditableField
          value={service.comments}
          onCancel={() =>
            setService({ ...service, comments: service.comments })
          }
          onChange={(value) => setService({ ...service, comments: value })}
          onEdit={() => {}}
          onSubmit={(value) => onSubmit({ ...service, comments: value })}
        ></EditableField>
      </Box>
    </VStack>
  );
};

export default ServiceDetails;
