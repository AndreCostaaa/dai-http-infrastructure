import { Center, Spinner } from "@chakra-ui/react";
import Cars from "./Cars";
import useCars from "../../hooks/useCars";
import DataSkeleton from "../generic/DataSkeleton";

const CarPanel = () => {
  const { data, isLoading } = useCars();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <Cars carList={data} />}
    </DataSkeleton>
  );
};

export default CarPanel;
