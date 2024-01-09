import { useEffect, useState } from "react";
import apiClient from "../services/api-client";
import { AxiosRequestConfig, CanceledError } from "axios";

const useData = <T>(
  apiEndPoint: string,
  requestConfig?: AxiosRequestConfig,
  deps?: any[]
) => {
  const [data, setData] = useState<T>();
  const [statusCode, setstatusCode] = useState(-1);
  const [isLoading, setLoading] = useState(false);

  useEffect(
    () => {
      const controller = new AbortController();
      setLoading(true);
      apiClient
        .get<T>(apiEndPoint, {
          signal: controller.signal,
          ...requestConfig,
        })
        .then((res) => {
          setData(res.data);
          setLoading(false);
          setstatusCode(res.status);
        })
        .catch((err) => {
          if (err instanceof CanceledError) {
            return;
          }
          setData(undefined);
          setstatusCode(err.response.status);
          setLoading(false);
        });

      return () => controller.abort();
    },
    deps ? [...deps] : []
  );

  return { data, setData, statusCode, isLoading };
};

export default useData;
