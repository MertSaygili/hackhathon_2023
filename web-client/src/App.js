import { useEffect, useState } from "react";
import { socket } from "./socket";
import RpCard from "./components/RpCard";
import { Box, Container } from "@mui/material";
import RpDialog from "./components/RpDialog";

function App() {
  const defaultRpInfo = {
    id: "",
    name: "",
    playlist: [],
  };
  const [open, setOpen] = useState(false);
  const [selectedRpInfo, setSelectedRpInfo] = useState(defaultRpInfo);
  const [rpClients, setRpClients] = useState([]);
  useEffect(() => {
    socket.emit("get-rp-clients");

    socket.on("get-rp-clients", (rp_clients) => {
      console.log("get-rp-clients", rp_clients);
      setRpClients(rp_clients);
      setSelectedRpInfo(defaultRpInfo);
      setOpen(false);
    });

    return () => {
      socket.off("get-rp-clients");
    };
  }, []);

  return (
    <Container>
      <RpDialog open={open} setOpen={setOpen} rp_info={selectedRpInfo} setSelectedRpInfo={setSelectedRpInfo} />
      <h1 style={{ textAlign: "center" }}>Scroll Up Devices</h1>
      <Box
        sx={{
          display: "flex",
          flexWrap: "wrap",
          justifyContent: "center",
        }}
      >
        {rpClients.map((rpClient) => {
          return (
            <RpCard
              key={rpClient.id}
              rp_info={rpClient}
              setName={(name) => {
                setRpClients((prev) => {
                  return prev.map((rp) => {
                    if (rp.id === rpClient.id) {
                      rp.name = name;
                    }
                    return rp;
                  });
                });
              }}
              setSelectedRpInfo={setSelectedRpInfo}
              setOpen={setOpen}
            />
          );
        })}
      </Box>
    </Container>
  );
}

export default App;
