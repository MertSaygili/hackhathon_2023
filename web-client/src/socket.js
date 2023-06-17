import {io} from 'socket.io-client';

const URL = 'http://localhost';

export const socket = io(URL);