import {
  Editable,
  EditableInput,
  EditablePreview,
  Input,
  VStack,
} from "@chakra-ui/react";
import EditableControls from "./EditableControls";

interface Props {
  value: string;
  onSubmit: (newValue: string) => void;
  onChange: (newValue: string) => void;
  onEdit: () => void;
  onCancel: () => void;
}
const EditableField = ({
  value,
  onSubmit,
  onChange,
  onEdit,
  onCancel,
}: Props) => {
  return (
    <Editable
      textAlign="center"
      value={value}
      //submitOnBlur={false}
      isPreviewFocusable={false}
      onSubmit={onSubmit}
      onChange={onChange}
      onEdit={onEdit}
      onCancel={onCancel}
    >
      <EditablePreview />
      <VStack>
        <Input as={EditableInput} />
        <EditableControls />
      </VStack>
    </Editable>
  );
};

export default EditableField;
