import useCarParts from "../../hooks/useCarParts";
import DataSkeleton from "../generic/DataSkeleton";
import CarParts from "./CarParts";

const CarPartPanel = () => {
  const { data, isLoading } = useCarParts();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <CarParts carPartList={data} />}
    </DataSkeleton>
  );
};

export default CarPartPanel;
