import Clients from "./Clients";
import DataSkeleton from "../generic/DataSkeleton";
import useClients from "../../hooks/useClients";
const ClientPanel = () => {
  const { data, isLoading } = useClients();

  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && <Clients clientList={data} />}
    </DataSkeleton>
  );
};

export default ClientPanel;
