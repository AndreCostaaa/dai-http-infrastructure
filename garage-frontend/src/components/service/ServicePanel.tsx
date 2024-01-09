import React from "react";
import Services from "./Services";
import DataSkeleton from "../generic/DataSkeleton";
import useServices from "../../hooks/useServices";

const ServicePanel = () => {
  const { data, isLoading } = useServices();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <Services serviceList={data} />}
    </DataSkeleton>
  );
};

export default ServicePanel;
