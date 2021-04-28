import axios from "axios";

let isDev = false;
if (!process.env.NODE_ENV || process.env.NODE_ENV === "development") {
  isDev = true;
}

const instance = axios.create({
  baseURL: isDev
    ? "http://localhost:8081"
    : "http://restaurant-api.patiphon.cf",
  // baseURL: "http://localhost:8081",
  withCredentials: true,
});

export default instance;
