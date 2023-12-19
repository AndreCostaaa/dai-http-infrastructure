import { ButtonProps, Button } from "@chakra-ui/react";

import { Link } from "react-router-dom";
interface Props extends ButtonProps {
  url: string;
  text: string;
}

const NavigationButton = (props: Props) => {
  return (
    <Button {...props} as={Link} to={props.url}>
      {props.text}
    </Button>
  );
};

export default NavigationButton;
