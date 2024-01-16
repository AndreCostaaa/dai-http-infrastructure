import { useState } from "react";
import {
  Button,
  FormControl,
  FormLabel,
  Heading,
  Input,
  NumberInput,
  NumberInputField,
  VStack,
  useToast,
} from "@chakra-ui/react";
import clientService, { Client } from "../../services/client-service";
import PersonCreateForm from "../person/PersonCreateForm";
import { Person } from "../../services/person-service";

const NewClient = () => {
  const [client, setClient] = useState<Client>({
    id: 0,
    firstName: "",
    lastName: "",
    phoneNo: "",
    email: "",
    street: "",
    streetNo: 0,
    npa: 0,
    country: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const toast = useToast();
  const onSubmit = (client: Client) => {
    if (
      client.firstName === "" ||
      client.lastName === "" ||
      client.phoneNo === "" ||
      client.email === "" ||
      client.street === "" ||
      client.streetNo === 0 ||
      client.npa === 0 ||
      client.country === ""
    ) {
      return;
    }
    setIsLoading(true);
    clientService
      .post(client)
      .then(() =>
        toast({
          title: "Client Créé",
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
      <Heading textAlign={"center"}>Nouveau Client</Heading>
      <PersonCreateForm
        person={client}
        setPerson={(person: Person) =>
          setClient({
            ...client,
            firstName: person.firstName,
            lastName: person.lastName,
            phoneNo: person.phoneNo,
          })
        }
      />
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Email</FormLabel>
        <Input
          value={client.email}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 30 &&
            setClient({ ...client, email: e.target.value })
          }
        />
      </FormControl>
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Rue</FormLabel>
        <Input
          value={client.street}
          onChange={(e) =>
            e.target.value.length <= 30 &&
            setClient({ ...client, street: e.target.value })
          }
          type="text"
        />
      </FormControl>
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Nº</FormLabel>
        <NumberInput
          defaultValue={0}
          value={client.streetNo}
          onChange={(_, value) => setClient({ ...client, streetNo: value })}
          min={1}
        >
          <NumberInputField />
        </NumberInput>
      </FormControl>
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Npa</FormLabel>
        <NumberInput
          defaultValue={0}
          value={client.npa}
          onChange={(_, value) => setClient({ ...client, npa: value })}
          min={1}
        >
          <NumberInputField />
        </NumberInput>
      </FormControl>
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Pays</FormLabel>
        <Input
          value={client.country}
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setClient({ ...client, country: e.target.value })
          }
          type="text"
        />
      </FormControl>
      <Button
        mb="5"
        colorScheme="teal"
        onClick={() => onSubmit(client)}
        isLoading={isLoading}
        type="submit"
      >
        Submit
      </Button>
    </VStack>
  );
};

export default NewClient;
