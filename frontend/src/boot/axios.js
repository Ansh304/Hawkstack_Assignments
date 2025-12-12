import axios from "axios";

let hostName = window.location.hostname;
let protocol = window.location.protocol;
let port = window.location.port;

let backendPort = 8080;
const BASE_URL = `${protocol}//${hostName}:${backendPort}/api/`;

const api = axios.create({
    baseURL: BASE_URL,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});


export { api, BASE_URL };
