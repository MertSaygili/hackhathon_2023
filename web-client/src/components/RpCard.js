import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { EditText, EditTextarea } from "react-edit-text";
import "react-edit-text/dist/index.css";
import { socket } from "../socket";

const bull = (
  <Box
    component="span"
    sx={{ display: "inline-block", mx: "2px", transform: "scale(0.8)" }}
  >
    •
  </Box>
);

export default function RpCard({
  rp_info,
  setSelectedRpInfo,
  setOpen,
  setName,
}) {
  return (
    <Box sx={{ width: 400, padding: "10px" }}>
      <Card variant="outlined">
        <React.Fragment>
          <CardContent>
            <Typography variant="h5" component="div">
              <EditText
                onChange={(e) => {
                  setName(e.target.value);
                }}
                value={rp_info.name}
                onSave={(e) => {
                  socket.emit("rp-change-name", {
                    id: rp_info.id,
                    name: e.value,
                  });
                }}
              />
            </Typography>
          </CardContent>
          <CardActions>
            <Button
              size="small"
              onClick={() => {
                setSelectedRpInfo(rp_info);
                setOpen(true);
              }}
            >
              Go
            </Button>
          </CardActions>
        </React.Fragment>
      </Card>
    </Box>
  );
}
