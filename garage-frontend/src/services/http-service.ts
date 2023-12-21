import apiClient, { mediaApiClient } from "./api-client.ts";
import { AxiosRequestConfig } from "axios";

export interface Entity {
  id: number;
}
export interface Person extends Entity {
  fname: string;
  lname: string;
  phoneCode: string;
  phoneNo: string;
}

export class HttpService {
  endpoint: string;

  constructor(endpoint: string) {
    this.endpoint = endpoint;
  }
  get<T>(params: T, config: AxiosRequestConfig) {
    return apiClient.get(this.endpoint, { ...config, params: params });
  }
  getMedia(id: string, config: AxiosRequestConfig) {
    return mediaApiClient.get(this.endpoint, { ...config, params: { id: id } });
  }
  delete(id: string | number, config: AxiosRequestConfig) {
    return apiClient.delete(this.endpoint, { ...config, params: { id: id } });
  }
  deleteCompositeKey(params: object, config: AxiosRequestConfig) {
    return apiClient.delete(this.endpoint, { ...config, params: params });
  }

  post<T>(entity: T, config?: AxiosRequestConfig) {
    return apiClient.post(this.endpoint, entity, config);
  }
  postMedia(data: FormData, config?: AxiosRequestConfig) {
    return mediaApiClient.post(this.endpoint, data, config);
  }
  update<T extends Entity>(
    id: string | number,
    entity: T,
    config: AxiosRequestConfig
  ) {
    return apiClient.patch(this.endpoint, entity, {
      ...config,
      params: { id: id },
    });
  }
}

const create = (endpoint: string) => new HttpService(endpoint);
export default create;
