import { ReactNode } from "react";
import { Skeleton } from "@chakra-ui/react";

interface Props {
  data: any;
  isLoading: boolean;
  children: ReactNode;
}
const DataSkeleton = ({ data, isLoading, children }: Props) => {
  if (!data && !isLoading) {
    return null;
  }
  return <Skeleton isLoaded={!isLoading}>{children}</Skeleton>;
};

export default DataSkeleton;
