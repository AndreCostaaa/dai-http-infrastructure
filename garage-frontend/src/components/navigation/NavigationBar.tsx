import {
  Button,
  Card,
  CardHeader,
  Heading,
  IconButton,
  VStack,
  ButtonProps,
} from "@chakra-ui/react";
import { GrServices } from "react-icons/gr";

import { FaCar, FaTools } from "react-icons/fa";

import { MdDashboard, MdPeople } from "react-icons/md";
import NavigationBarButton from "./NavigationBarButton";
interface Props extends ButtonProps {
  url: string;
}

const NavigationBar = () => {
  const buttons = [
    {
      url: "/",
      text: "Dashboard",
      icon: MdDashboard,
    },
    {
      url: "/services",
      text: "Service",
      icon: GrServices,
    },
    {
      url: "/clients",
      text: "Clients",
      icon: MdPeople,
    },
    {
      url: "/cars",
      text: "Voitures",
      icon: FaCar,
    },
    {
      url: "/parts",
      text: "Pièces",
      icon: FaTools,
    },
    {
      url: "/employees",
      text: "Employés",
      icon: MdPeople,
    },
  ];
  return (
    <VStack width="100%" padding="20px">
      {buttons.map((btn, i) => (
        <NavigationBarButton
          key={i}
          url={btn.url}
          text={btn.text}
          icon={btn.icon}
        />
      ))}
    </VStack>
  );
};

export default NavigationBar;
