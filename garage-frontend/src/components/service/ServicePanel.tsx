import Services from "./Services";
import DataSkeleton from "../generic/DataSkeleton";
import useServices from "../../hooks/useServices";
import serviceClient, { Service } from "../../services/service-client";
import { useToast } from "@chakra-ui/react";

const ServicePanel = () => {
  const { data, isLoading, setData } = useServices();
  const toast = useToast();
  const onDelete = (service: Service) => {
    if (!data) return;
    serviceClient
      .delete(service.id)
      .then(() => {
        toast({
          title: "Supprimé",
          status: "success",
          duration: 5000,
          isClosable: true,
        });
        setData(data.filter((svc) => svc.id !== service.id));
      })
      .catch((error) => {
        const description =
          error.response?.data.title || "Connexion Impossible";
        toast({
          title: "Erreur",
          status: "error",
          description: description,
          duration: 5000,
          isClosable: true,
        });
      });
  };
  return (
    <DataSkeleton isLoading={isLoading} data={data}>
      {data && (
        <Services
          serviceList={data}
          onUpdate={(service) =>
            setData(data.map((svc) => (svc.id === service.id ? service : svc)))
          }
          onDelete={onDelete}
        />
      )}
    </DataSkeleton>
  );
};

export default ServicePanel;
