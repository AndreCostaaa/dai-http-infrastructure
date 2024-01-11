import axios from "axios";
const headers = {
  "Content-Type": "application/json",
};

function apiDomain() {
  const { hostname } = window.location;
  return `https://api.${hostname}/api`;
}

export default axios.create({
  baseURL: apiDomain(),
  headers: headers,
});

export const mediaApiClient = axios.create({
  baseURL: apiDomain(),
  responseType: "blob",
  headers: { "Content-Type": "multipart/form-data" },
});
