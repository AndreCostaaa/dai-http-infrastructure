import apiClient, { apiDomain, mediaApiClient } from "../services/api-client";
import { Service } from "../services/service-client";

export function incrementState(
  service: Service,
  onUpdate: (service: Service) => void
) {
  apiClient.patch(`/services/${service.id}`).then(({ data }) => onUpdate(data));
}

export function sendImages(service: Service, fileList: FileList | null) {
  if (!fileList) {
    return null;
  }
  var formData = new FormData();
  for (let i = 0; i < fileList.length; ++i) {
    formData.append(`image${i}`, fileList[i]);
  }
  return mediaApiClient.post(`/media/${service.id}`, formData);
}

export function getImageNames(
  service: Service,
  setFiles: (fileNames: string[]) => void
) {
  apiClient
    .get(`/media/${service.id}`)
    .then(({ data }) =>
      setFiles(
        data.map((d: string) => apiDomain() + `/media/${service.id}/` + d)
      )
    );
}
