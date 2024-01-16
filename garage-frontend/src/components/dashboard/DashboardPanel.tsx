import { Button, Heading, VStack, useDisclosure } from "@chakra-ui/react";
import NewClientModal from "../client/NewClientModal";
import NewEmployeeModal from "../employee/NewEmployeeModal";
import NewServiceModal from "../service/NewServiceModal";
import NewCarModal from "../car/NewCarModal";
import NewCarPartModal from "../car-part/NewCarPartModal";

const DashboardPanel = () => {
  const objs = [
    {
      header: "Nouveau Service",
      disclosure: useDisclosure(),
      Modal: NewServiceModal,
    },
    {
      header: "Nouvelle Voiture",
      disclosure: useDisclosure(),
      Modal: NewCarModal,
    },
    {
      header: "Nouveau Client",
      disclosure: useDisclosure(),
      Modal: NewClientModal,
    },
    {
      header: "Nouveau Employé",
      disclosure: useDisclosure(),
      Modal: NewEmployeeModal,
    },
    {
      header: "Nouvelle Pièce",
      disclosure: useDisclosure(),
      Modal: NewCarPartModal,
    },
  ];

  return (
    <VStack spacing={5} justifyContent={"left"}>
      <Heading>Bienvenue!</Heading>
      {objs.map(({ header, disclosure, Modal }) => (
        <>
          <Button onClick={disclosure.onOpen}>{header}</Button>
          {
            <Modal
              onClose={disclosure.onClose}
              isOpen={disclosure.isOpen}
              children={null}
            />
          }
        </>
      ))}
    </VStack>
  );
};

export default DashboardPanel;
