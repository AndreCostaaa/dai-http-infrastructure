import { Service } from "../../services/service-client";
import {
  Box,
  Button,
  HStack,
  Heading,
  Image,
  NumberDecrementStepper,
  NumberIncrementStepper,
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  Tooltip,
  VStack,
  useDisclosure,
  useToast,
} from "@chakra-ui/react";
import useCustomScrollbar from "../../hooks/useCustomScrollbar";
import { Client } from "../../services/client-service";
import Cars from "../car/Cars";
import Clients from "../client/Clients";
import AssignMechanicModal from "../employee/AssignMechanicModal";
import Employees from "../employee/Employees";
import EditableField from "../generic/EditableField";
import {
  getImageNames,
  incrementState,
  sendImages,
} from "../../actions/service-actions";
import ServiceBillModal from "../bill/ServiceBillModal";
import FileUploadInput from "../generic/FileUploadInput";
import { useEffect, useState } from "react";
import ImageModal from "./ImageModal";
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
  const {
    onOpen: onOpenBill,
    isOpen: isOpenBill,
    onClose: onCloseBill,
  } = useDisclosure();
  const {
    onOpen: onOpenImages,
    isOpen: isOpenImages,
    onClose: onCloseImages,
  } = useDisclosure();

  const [files, setFiles] = useState<FileList | null>(null);
  const [imageUrls, setImageUrls] = useState<string[]>();
  const toast = useToast();
  useEffect(
    () => getImageNames(service, (imageNames) => setImageUrls(imageNames)),
    []
  );

  return (
    <>
      <VStack
        spacing={10}
        width="100%"
        overflowY="auto"
        sx={useCustomScrollbar()}
      >
        <Heading textAlign={"center"}>Service #{service.id}</Heading>
        <Box width={"100%"}>
          <Heading size="lg" textAlign={"center"}>
            Client
          </Heading>
          <Clients clientList={[service.client] as Client[]} />
        </Box>
        <Box width={"100%"}>
          <Heading size="lg" textAlign="center">
            Voiture
          </Heading>
          <Cars carList={[service.car]} />
        </Box>
        <Box width={"100%"} textAlign={"center"}>
          <Heading size="lg" textAlign={"center"}>
            Mecanicien
          </Heading>
          {service.mechanic && <Employees employeeList={[service.mechanic]} />}
          <Button justifyContent="center" onClick={onOpenAssign} mt="2">
            {service.mechanic ? "Modifier" : "Assigner"}
          </Button>
          <AssignMechanicModal
            isOpen={isOpenAssign}
            onClose={onCloseAssign}
            children={null}
            onSelect={(mechanic) =>
              onSubmit({ ...service, mechanic: mechanic })
            }
          />
        </Box>

        <HStack textAlign={"center"} width={"100%"}>
          <Box width="50%">
            <Heading size="lg">État Actuel</Heading>
            <Tooltip label={service.state.description}>
              <Heading size="md">{service.state.title}</Heading>
            </Tooltip>
          </Box>
          <Box width={"50%"}>
            {service.nextState ? (
              <>
                <Tooltip
                  label={
                    service.nextState.title +
                    ": " +
                    service.nextState.description
                  }
                >
                  <Button
                    onClick={() =>
                      incrementState(service, (service: Service) =>
                        setService(service)
                      )
                    }
                  >
                    Passer au prochain état
                  </Button>
                </Tooltip>
              </>
            ) : (
              <>
                <Button onClick={onOpenBill}>Afficher Facture</Button>
                {isOpenBill && (
                  <ServiceBillModal
                    isOpen={isOpenBill}
                    onClose={onCloseBill}
                    serviceBillId={service.id}
                    children={null}
                  />
                )}
              </>
            )}
          </Box>
        </HStack>
        {service.mechanic && (
          <>
            <Box width="100%" textAlign={"center"}>
              <Heading size="lg">Heures</Heading>
              <NumberInput
                textAlign={"center"}
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
              <Button
                mt="3"
                mb="5"
                colorScheme="teal"
                onClick={() => onSubmit(service)}
                type="submit"
              >
                Submit
              </Button>
            </Box>
            <Box>
              <Heading size="lg">Commentaires</Heading>
              <EditableField
                value={service.comments}
                onCancel={() =>
                  setService({ ...service, comments: service.comments })
                }
                onChange={(value) =>
                  setService({ ...service, comments: value })
                }
                onEdit={() => {}}
                onSubmit={(value) => onSubmit({ ...service, comments: value })}
              ></EditableField>
            </Box>
            <Box>
              <Heading size="lg">Images</Heading>
              <VStack>
                <FileUploadInput
                  onFileUpload={(fileList: FileList | null) =>
                    setFiles(fileList)
                  }
                />
                <Button
                  colorScheme="teal"
                  onClick={() =>
                    sendImages(service, files)
                      ?.then(() =>
                        toast({
                          title: "Images Téléchargées",
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
                  }
                  type="submit"
                  mt="2"
                >
                  Telecharger
                </Button>
              </VStack>
            </Box>
            {imageUrls && (
              <Box textAlign={"center"}>
                <Button onClick={onOpenImages}>Afficher Images</Button>
                <ImageModal
                  isOpen={isOpenImages}
                  onClose={onCloseImages}
                  imageList={imageUrls}
                  children={null}
                ></ImageModal>
              </Box>
            )}
          </>
        )}
      </VStack>
    </>
  );
};

export default ServiceDetails;
