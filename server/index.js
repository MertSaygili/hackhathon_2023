const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server,{
    cors: {
        origin: "*",
        }
    }
);


let rpClients = [];

app.get("/", (req, res) => {
  res.send("hi")
});

io.on("connection", (socket) => {
  console.log("a user connected", socket.id);

  socket.on("rp-login", (rp_info) => {
    // rpClients.push({
    //     id:socket.id,
    //     playlist:msg.playlist
    // })
    if(!rpClients.find((rp)=>rp.id===socket.id)){
      rpClients.push({id:socket.id, ...rp_info})
      socket.broadcast.emit("get-rp-clients", rpClients);

    }
  });

  socket.on("rp-change-name", (rp_info) => {
    const info = JSON.parse(rp_info)
    const id = info.id
    const name = info.name
    if (rpClients.find((rp) => rp.id === socket.id)) {
      rpClients.find((rp) => rp.id === socket.id).name = name;
      io.sockets.to(id).emit("rp-change-name", name);
      
    }
  });

  socket.on("get-rp-clients", () => {
    socket.emit("get-rp-clients", rpClients);
  })

  socket.on("disconnect", () => {
    console.log("user disconnected");
    rpClients = rpClients.filter(rp_info=>rp_info.id!=socket.id)
    socket.broadcast.emit("get-rp-clients", rpClients);
  });
});

server.listen(80, () => {
  console.log("listening on *:80");
});
