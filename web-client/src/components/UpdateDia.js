import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import Slider from "@mui/material/Slider";
import { socket } from "../socket";
import { DialogContent } from "@mui/material";

export default function DraggableDialog({
  open,
  setOpen,
  rp_id,
  defaultPlayTime,
  updateId,
  setUpdateId,
}) {
  const handleClose = () => {
    setOpen(false);
  };

  const [playTime, setPlayTime] = React.useState(defaultPlayTime);

  React.useEffect(() => {
    setPlayTime(defaultPlayTime);
  }, [defaultPlayTime]);

  const UpdateItem = () => {
    socket.emit("rp-update-playlist", {
      id: rp_id,
      item_id: updateId,
      play_time: playTime,
    });
    setUpdateId(null);
    setOpen(false);
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle id="draggable-dialog-title">Update Play Time</DialogTitle>
        <DialogContent>
          <div style={{margin:"5em 0"}}>
            <Slider
              size="small"
              onChange={(e, v) => {
                setPlayTime(v);
              }}
              step={0.1}
              min={1}
              max={60}
              value={playTime}
              aria-label="Small"
              valueLabelDisplay="auto"
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            Cancel
          </Button>
          <Button onClick={UpdateItem}>Accept</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
