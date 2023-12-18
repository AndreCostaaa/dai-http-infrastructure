import {
  Button,
  Card,
  CardHeader,
  Heading,
  IconButton,
  VStack,
} from "@chakra-ui/react";
import { GrServices } from "react-icons/gr";

import { FaCar, FaTools } from "react-icons/fa";

import { MdPeople } from "react-icons/md";

const NavBar = () => {
  return (
    <VStack width="100%" padding="20px">
      <Button width="100%" rightIcon={<GrServices />} size={"lg"}>
        Service
      </Button>
      <Button width="100%" rightIcon={<MdPeople />} size={"lg"}>
        Employés
      </Button>
      <Button width="100%" rightIcon={<MdPeople />} size={"lg"}>
        Clients
      </Button>
      <Button width="100%" rightIcon={<FaCar />} size={"lg"}>
        Voitures
      </Button>
      <Button width="100%" rightIcon={<FaTools />} size={"lg"}>
        Pièces
      </Button>
    </VStack>
  );
};

export default NavBar;
