import { ReactElement } from "react";
import NavigationButton from "./NavigationButton";
interface Props {
  url: string;
  icon: ReactElement;
  text: string;
}
const NavigationBarButton = ({ url, text, icon }: Props) => {
  return (
    <NavigationButton
      url={url}
      text={text}
      rightIcon={icon}
      width={"100%"}
      size="lg"
    />
  );
};

export default NavigationBarButton;
