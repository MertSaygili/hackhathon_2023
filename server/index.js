const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server, {
  cors: {
    origin: "*",
  },
});

let rpClients = [
];

app.get("/", (req, res) => {
  res.send("hi");
});

io.on("connection", (socket) => {
  console.log("a user connected", socket.id);

  socket.on("rp-login", (rp_info) => {
    // rpClients.push({
    //     id:socket.id,
    //     playlist:msg.playlist
    // })
    if (!rpClients.find((rp) => rp.id === socket.id)) {
      rpClients.push({ id: socket.id, ...rp_info });
      socket.broadcast.emit("get-rp-clients", rpClients);
    }
  });

  socket.on("rp-change-name", (rp_info) => {
    console.log("changeee",rp_info);
    const id = rp_info.id;
    const name = rp_info.name;
    if (rpClients.find((rp) => rp.id === rp_info.id)) {
      const index = rpClients.findIndex((rp) => rp.id === rp_info.id);
      rpClients[index]["name"] = name;
      io.sockets.to(id).emit("rp-change-name", name);
      socket.broadcast.emit("get-rp-clients", rpClients);
      io.sockets.to(socket.id).emit("get-rp-clients", rpClients);
    }
  });

  socket.on("get-rp-clients", () => {
    socket.emit("get-rp-clients", rpClients);
  });

  socket.on("disconnect", () => {
    console.log("user disconnected");
    rpClients = rpClients.filter((rp_info) => rp_info.id != socket.id);
    socket.broadcast.emit("get-rp-clients", rpClients);
  });
});

server.listen(80, () => {
  console.log("listening on *:80");
});
