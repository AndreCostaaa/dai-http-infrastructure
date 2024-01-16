import { useState } from "react";
import {
  Button,
  FormControl,
  FormLabel,
  Heading,
  VStack,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import serviceClient, { Service } from "../../services/service-client";
import ClientPickerModal from "../client/ClientPickerModal";
import { Client } from "../../services/client-service";
import CarPickerModal from "../car/CarPickerModal";
import { Car } from "../../services/car-service";

const NewService = () => {
  const [isLoading, setIsLoading] = useState(false);

  const [car, setCar] = useState<Car | null>(null);
  const [client, setClient] = useState<Client | null>(null);
  const {
    onOpen: onOpenClient,
    isOpen: isOpenClient,
    onClose: onCloseClient,
  } = useDisclosure();
  const {
    onOpen: onOpenCar,
    isOpen: isOpenCar,
    onClose: onCloseCar,
  } = useDisclosure();

  const toast = useToast();
  const onSubmit = (car: Car, client: Client) => {
    if (!car || !client) {
      return;
    }
    setIsLoading(true);
    serviceClient
      .post({
        car: car,
        client: client,
        mechanic: null,
        comments: "",
        dateCarArrival: null,
        dateCarDone: null,
        dateCarLeft: null,
        dateCarProcessing: null,
        dateCreated: new Date(),
        hasPictures: false,
        hoursWorked: 0,
        state: { id: 1 },
      } as Service)
      .then(({ data }) =>
        toast({
          title: "Service Créé #" + data.id,
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
      <Heading textAlign={"center"}>Nouveau Service</Heading>
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Client</FormLabel>
        <Button onClick={onOpenClient}>
          {client ? client.firstName + " " + client.lastName : "Selectionner"}
        </Button>
        <ClientPickerModal
          isOpen={isOpenClient}
          onClose={onCloseClient}
          onSelect={(client: Client) => {
            setClient(client);
            onCloseClient();
          }}
          children={null}
        />
      </FormControl>
      {client && (
        <FormControl textAlign={"center"}>
          <FormLabel textAlign={"center"}>Car</FormLabel>

          <Button onClick={onOpenCar}>
            {car ? car.brand + " " + car.model : "Selectionner"}
          </Button>
          {isOpenCar && (
            <CarPickerModal
              children={null}
              clientId={client.id}
              onSelect={(car: Car) => {
                setCar(car);
                onCloseCar();
              }}
              isOpen={isOpenCar}
              onClose={onCloseCar}
            />
          )}
        </FormControl>
      )}
      {car && client && (
        <Button
          mb="5"
          colorScheme="teal"
          onClick={() => onSubmit(car, client)}
          isLoading={isLoading}
          type="submit"
        >
          Submit
        </Button>
      )}
    </VStack>
  );
};

export default NewService;
