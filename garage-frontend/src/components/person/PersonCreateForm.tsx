import { FormControl, FormLabel, Input } from "@chakra-ui/react";
import { Person } from "../../services/person-service";

interface Props {
  person: Person;
  setPerson: (person: Person) => void;
}
const PersonCreateForm = ({ person, setPerson }: Props) => {
  return (
    <>
      <FormControl>
        <FormLabel>Prénom</FormLabel>
        <Input
          value={person.firstName}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setPerson({ ...person, firstName: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Nom</FormLabel>
        <Input
          value={person.lastName}
          type="text"
          onChange={(e) =>
            e.target.value.length <= 15 &&
            setPerson({ ...person, lastName: e.target.value })
          }
        />
      </FormControl>
      <FormControl>
        <FormLabel>Natel</FormLabel>
        <Input
          value={person.phoneNo}
          type="text"
          onChange={(e) =>
            !Number.isNaN(parseInt(e.target.value)) &&
            e.target.value.length <= 15 &&
            setPerson({ ...person, phoneNo: e.target.value })
          }
        />
      </FormControl>
    </>
  );
};

export default PersonCreateForm;
