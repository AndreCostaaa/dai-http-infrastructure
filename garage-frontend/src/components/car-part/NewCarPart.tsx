import { useState } from "react";
import carService, { Car } from "../../services/car-service";
import {
  Button,
  FormControl,
  FormLabel,
  Heading,
  Input,
  NumberDecrementStepper,
  NumberIncrementStepper,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  VStack,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import { Client } from "../../services/client-service";
import ClientPickerModal from "../client/ClientPickerModal";
import carPartService, { CarPart } from "../../services/car-part-service";
import AssignServiceModal from "../service/AssignServiceModal";
import { Service } from "../../services/service-client";

const NewCarPart = () => {
  const [part, setPart] = useState<CarPart>({
    id: 0,
    buyPrice: 0,
    sellPrice: 0,
    name: "",
    serviceId: 0,
    supplier: "",
    supplierRef: "",
    description: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [serviceName, setServiceName] = useState("");
  const toast = useToast();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const onSubmit = (part: CarPart) => {
    if (
      part.buyPrice === 0 ||
      part.sellPrice === 0 ||
      part.name === "" ||
      part.supplier === "" ||
      part.sellPrice === 0 ||
      part.supplierRef === ""
    ) {
      return;
    }
    setIsLoading(true);
    carPartService
      .post(part)
      .then(() =>
        toast({
          title: "Pièce Crée",
          status: "success",
          duration: 5000,
          isClosable: true,
        })
      )
      .catch((error) => {
        const description =
          error.response?.data.title || "Connexion Impossible";
        toast({
          title: "Erreur",
          status: "error",
          description: description,
          duration: 5000,
          isClosable: true,
        });
      })
      .finally(() => setIsLoading(false));
  };
  return (
    <VStack>
      <Heading textAlign={"center"}>Nouvelle Pièce</Heading>
      <FormControl>
        <FormLabel>Nom</FormLabel>
        <Input
          value={part.name}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 50 &&
            setPart({ ...part, name: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Description</FormLabel>
        <Input
          value={part.description}
          onChange={(e) =>
            e.target.value.length <= 100 &&
            setPart({ ...part, description: e.target.value })
          }
          type="text"
        />
      </FormControl>
      <FormControl>
        <FormLabel>Fournisseur</FormLabel>
        <Input
          value={part.supplier}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 30 &&
            setPart({ ...part, supplier: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Ref. Fournisseur</FormLabel>
        <Input
          value={part.supplierRef}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 20 &&
            setPart({ ...part, supplierRef: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Prix d'achat</FormLabel>
        <NumberInput
          defaultValue={0}
          value={part.buyPrice}
          onChange={(_, value) => setPart({ ...part, buyPrice: value })}
          min={0}
          precision={2}
          step={0.2}
        >
          <NumberInputField />
        </NumberInput>
      </FormControl>
      <FormControl>
        <FormLabel>Prix de vente</FormLabel>
        <NumberInput
          defaultValue={0}
          value={part.sellPrice}
          onChange={(_, value) => setPart({ ...part, sellPrice: value })}
          min={0}
          precision={2}
          step={0.2}
        >
          <NumberInputField />
        </NumberInput>
      </FormControl>
      <FormControl>
        <FormLabel>Service</FormLabel>
        <Button onClick={onOpen}>
          {part.serviceId !== 0 ? serviceName : "Assigner"}
        </Button>
        {isOpen && (
          <AssignServiceModal
            isOpen={isOpen}
            onClose={onClose}
            onAssign={(service: Service) => {
              setPart({ ...part, serviceId: service.id });
              setServiceName(
                service.car.brand + " " + service.car.model + " #" + service.id
              );
              onClose();
            }}
            children={null}
          />
        )}
      </FormControl>
      <Button
        mb="5"
        colorScheme="teal"
        onClick={() => onSubmit(part)}
        isLoading={isLoading}
        type="submit"
      >
        Submit
      </Button>
    </VStack>
  );
};

export default NewCarPart;
