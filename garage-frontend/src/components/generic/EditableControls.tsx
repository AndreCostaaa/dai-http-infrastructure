import {
  ButtonGroup,
  Flex,
  IconButton,
  useEditableControls,
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon, EditIcon } from "@chakra-ui/icons";

const EditableControls = () => {
  const { isEditing, getCancelButtonProps, getEditButtonProps } =
    useEditableControls();

  return isEditing ? (
    <ButtonGroup justifyContent="center" size="sm">
      <IconButton
        aria-label="check"
        icon={<CheckIcon />}
        borderRadius={"20px"}
        borderWidth={"2px"}
        {...getCancelButtonProps()}
      />
      <IconButton
        aria-label="close"
        icon={<CloseIcon />}
        borderRadius={"20px"}
        borderWidth={"2px"}
        {...getCancelButtonProps()}
      />
    </ButtonGroup>
  ) : (
    <Flex justifyContent="center">
      <IconButton
        isRound={true}
        aria-label="edit"
        size="sm"
        borderRadius={"20px"}
        borderWidth={"2px"}
        icon={<EditIcon />}
        {...getEditButtonProps()}
      />
    </Flex>
  );
};

export default EditableControls;
