import * as React from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Slider from "@mui/material/Slider";
import { socket } from "../socket";

export default function AlertDialog({ open, setOpen, rp_info,setSelectedRpInfo }) {
  const [loading, setLoading] = React.useState(false);
  const [file, setFile] = React.useState(null);
  const [playTime, setPlayTime] = React.useState(5);
  const [type, setType] = React.useState("image");
  const [url, setUrl] = React.useState(null);
  function handleChange(e) {
    setFile(e.target.files[0]);
    setType(e.target.files[0].type.split("/")[0]);
    setUrl(URL.createObjectURL(e.target.files[0]));
  }
  const handleClose = () => {
    setOpen(false);
  };

  const addItem = () => {
    setLoading(true);
    fetch("https://httpbin.org/post", {
      method: "POST",
      body: file,
      // 👇 Set headers manually for single file upload
      headers: {
        "content-type": file.type,
        "content-length": `${file.size}`, // 👈 Headers need to be a string
      },
    })
      .then((res) => res.json())
      .then(async (data) => {
        console.log(data.data);
        await socket.emit("rp-add-playlist", {
          id: rp_info.id,
          item: {
            name: file.name,
            type: type,
            play_time: playTime,
            file: data.data,
          },
        });
        setSelectedRpInfo((prev) => {
          return {
            ...prev,
            playlist: [
              ...prev.playlist,
              {
                name: file.name,
                type: type,
                play_time: playTime,
                file: data.data,
              },
            ],
          };
        });
        setLoading(false);
        setOpen(false);
      })
      .catch((err) => console.error(err));
  };

  React.useEffect(() => {
    setFile(null);
    setPlayTime(5);
    setType("image");
    setUrl(null);
  }, [open]);

  return (
    <Dialog
      open={open}
      onClose={handleClose}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">
        {"Add New Item to Playlist"}
      </DialogTitle>
      <DialogContent>
        <input
          type="file"
          id="myFile"
          name="filename"
          accept="image/*,video/*"
          onChange={handleChange}
        />
        <div>
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
        <div
          style={{
            marginTop: "1em",
          }}
        >
          {type === "image" ? (
            <img src={url} width={100} />
          ) : (
            <video src={url} width={100} autoPlay />
          )}
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Cancel</Button>
        <Button
          onClick={addItem}
          autoFocus
          disabled={file === null || loading}
        >
          Save
        </Button>
      </DialogActions>
    </Dialog>
  );
}
