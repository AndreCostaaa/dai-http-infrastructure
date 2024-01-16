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
import PersonCreateForm from "../person/PersonCreateForm";
import { Person } from "../../services/person-service";
import employeeService, { Employee } from "../../services/employee-service";
import { Specialization } from "../../services/specialization-service";
import { Role } from "../../services/role-service";
import RolePickerModal from "../role/RolePickerModal";
import SpecializationPickerModal from "../specialization/SpecializationPickerModal";

const NewEmployee = () => {
  const [employee, setEmployee] = useState<Employee>({
    id: 0,
    firstName: "",
    lastName: "",
    phoneNo: "",
    roleId: 0,
    specializationId: 0,
  });
  const [isLoading, setIsLoading] = useState(false);
  const {
    onOpen: onOpenRole,
    isOpen: isOpenRole,
    onClose: onCloseRole,
  } = useDisclosure();
  const {
    onOpen: onOpenSpecialization,
    isOpen: isOpenSpecialization,
    onClose: onCloseSpecialization,
  } = useDisclosure();
  const [role, setRole] = useState<Role>();
  const [specialization, setSpecialization] = useState<Specialization>();
  const toast = useToast();
  const onSubmit = (employee: Employee) => {
    if (
      employee.firstName === "" ||
      employee.lastName === "" ||
      employee.phoneNo === "" ||
      employee.roleId === 0
    ) {
      return;
    }
    setIsLoading(true);
    employeeService
      .post(employee)
      .then(() =>
        toast({
          title: "Employé Créé",
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
      <Heading textAlign={"center"}>Nouveau Employé</Heading>
      <PersonCreateForm
        person={employee}
        setPerson={(person: Person) =>
          setEmployee({
            ...employee,
            firstName: person.firstName,
            lastName: person.lastName,
            phoneNo: person.phoneNo,
          })
        }
      />
      <FormControl textAlign={"center"}>
        <FormLabel textAlign={"center"}>Role</FormLabel>

        <Button onClick={onOpenRole}>
          {role ? role.name : "Selectionner"}
        </Button>
        {isOpenRole && (
          <RolePickerModal
            isOpen={isOpenRole}
            onClose={onCloseRole}
            onSelect={(role: Role) => {
              setRole(role);
              setEmployee({
                ...employee,
                roleId: role.id,
                specializationId: 1,
              });
              onCloseRole();
            }}
            children={null}
          />
        )}
      </FormControl>
      {role && role.isMechanic && (
        <FormControl textAlign={"center"}>
          <FormLabel textAlign={"center"}>Specialisation</FormLabel>

          <Button onClick={onOpenSpecialization}>
            {specialization ? specialization.name : "Selectionner"}
          </Button>

          <SpecializationPickerModal
            isOpen={isOpenSpecialization}
            onClose={onCloseSpecialization}
            onSelect={(specialization: Specialization) => {
              setSpecialization(specialization);
              setEmployee({
                ...employee,
                specializationId: specialization.id,
              });
              onCloseSpecialization();
            }}
            children={null}
          />
        </FormControl>
      )}
      <Button
        mb="5"
        colorScheme="teal"
        onClick={() => onSubmit(employee)}
        isLoading={isLoading}
        type="submit"
      >
        Submit
      </Button>
    </VStack>
  );
};

export default NewEmployee;
