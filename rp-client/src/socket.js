import {io} from "socket.io-client";

const URL = "http://ombayus.local";

export const socket = io(URL);