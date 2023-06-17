const express = require("express");
const app = express();
const { v4: uuidv4 } = require("uuid");
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server, {
  cors: {
    origin: "*",
  },
});

let rpClients = [];

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
    console.log("rpClients", rpClients);
  });

  socket.on("rp-change-name", (rp_info) => {
    console.log("changeee", rp_info);
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

  socket.on("rp-add-playlist", (item) => {
    console.log("add playlist", item);
    if (rpClients.find((rp) => rp.id === item.id)) {
      const index = rpClients.findIndex((rp) => rp.id === item.id);
      const newId = uuidv4();
      rpClients[index]["playlist"].push({ id: newId, ...item.item });
      io.sockets
        .to(item.id)
        .emit("rp-add-playlist", { id: newId, ...item.item });
      socket.broadcast.emit("get-rp-clients", rpClients);
      io.sockets.to(socket.id).emit("get-rp-clients", rpClients);
    }
  });

  socket.on("rp-delete-playlist", (item) => {
    console.log("delete playlist", item);
    if (rpClients.find((rp) => rp.id === item.id)) {
      const index = rpClients.findIndex((rp) => rp.id === item.id);
      rpClients[index]["playlist"] = rpClients[index]["playlist"].filter(
        (p_item) => p_item.id != item.item_id
      );
      io.sockets.to(item.id).emit("rp-delete-playlist", item.item_id);
      socket.broadcast.emit("get-rp-clients", rpClients);
      io.sockets.to(socket.id).emit("get-rp-clients", rpClients);
    }
  });

  socket.on("rp-update-playlist", (item) => {
    console.log("update playlist", item);
    if (rpClients.find((rp) => rp.id === item.id)) {
      const index = rpClients.findIndex((rp) => rp.id === item.id);
      rpClients[index]["playlist"] = rpClients[index]["playlist"].map(
        (p_item) => {
          if (p_item.id === item.item_id) {
            p_item.play_time = item.play_time;
          }
          return p_item;
        }
      );
      io.sockets
        .to(item.id)
        .emit("rp-update-playlist", {
          id: item.item_id,
          play_time: item.play_time,
        });
      socket.broadcast.emit("get-rp-clients", rpClients);
      io.sockets.to(socket.id).emit("get-rp-clients", rpClients);
    }
  });


  socket.on("rp-play", (item) => {
    console.log("play", item);
    if (rpClients.find((rp) => rp.id === item.id)) {
      io.sockets.to(item.id).emit("rp-play", item.item_id);
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
