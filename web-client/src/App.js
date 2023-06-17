import { useEffect, useState } from "react";
import { socket } from "./socket";

function App() {
  const [rpClients, setRpClients] = useState([]);
  useEffect(() => {
    socket.emit("get-rp-clients");

    socket.on("get-rp-clients", (rp_clients) => {
      setRpClients(rp_clients);
    });

    return () => {
      socket.off("get-rp-clients");
    };
  }, []);

  return (
    <div className="App">
      {rpClients.map((rpClient) => {
        return (
          <div key={rpClient.id}>
            <h1>{rpClient.name}</h1>
          </div>
        );
      })}
    </div>
  );
}

export default App;
