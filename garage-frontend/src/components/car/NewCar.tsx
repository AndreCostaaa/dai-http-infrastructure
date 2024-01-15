import { useState } from "react";
import carService, { Car } from "../../services/car-service";
import {
  Button,
  FormControl,
  FormLabel,
  Heading,
  Input,
  VStack,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import { Client } from "../../services/client-service";
import ClientPickerModal from "../client/ClientPickerModal";

const NewCar = () => {
  const [car, setCar] = useState<Car>({
    id: 0,
    brand: "",
    chassisNo: "",
    color: "",
    model: "",
    ownerId: 0,
    recType: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [ownerName, setOwnerName] = useState("");
  const toast = useToast();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const onSubmit = (car: Car) => {
    if (
      car.brand === "" ||
      car.chassisNo === "" ||
      car.color === "" ||
      car.model === "" ||
      car.ownerId === 0 ||
      car.recType === "0"
    ) {
      return;
    }
    setIsLoading(true);
    carService
      .post(car)
      .then(() =>
        toast({
          title: "Voiture Crée",
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
      <Heading textAlign={"center"}>Nouvelle Voiture</Heading>
      <FormControl>
        <FormLabel>Marque</FormLabel>
        <Input
          value={car.brand}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setCar({ ...car, brand: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Modèle</FormLabel>
        <Input
          value={car.model}
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setCar({ ...car, model: e.target.value })
          }
          type="text"
        />
      </FormControl>
      <FormControl>
        <FormLabel>No Chassis</FormLabel>
        <Input
          value={car.chassisNo}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setCar({ ...car, chassisNo: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Reception par Type</FormLabel>
        <Input
          value={car.recType}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 6 &&
            setCar({ ...car, recType: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Couleur</FormLabel>
        <Input
          value={car.color}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setCar({ ...car, color: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Propriètaire</FormLabel>
        <Button onClick={onOpen}>
          {car.ownerId !== 0 ? ownerName : "Selectionner"}
        </Button>
        {isOpen && (
          <ClientPickerModal
            isOpen={isOpen}
            onClose={onClose}
            onSelect={(client: Client) => {
              setCar({ ...car, ownerId: client.id });
              setOwnerName(client.firstName + " " + client.lastName);
              onClose();
            }}
            children={null}
          />
        )}
      </FormControl>
      <Button
        mb="5"
        colorScheme="teal"
        onClick={() => onSubmit(car)}
        isLoading={isLoading}
        type="submit"
      >
        Submit
      </Button>
    </VStack>
  );
};

export default NewCar;
