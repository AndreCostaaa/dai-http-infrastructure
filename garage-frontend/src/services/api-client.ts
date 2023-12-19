import axios from "axios";
const headers = {
  "Content-Type": "application/json",
};

function apiDomain() {
  return "http://localhost:5000/api/v1";
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
