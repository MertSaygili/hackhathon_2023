import { Container, Button, Grid } from "@mui/material";
import { socket } from "../socket";
import React from "react";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ImageIcon from "@mui/icons-material/Image";
import VideocamIcon from "@mui/icons-material/Videocam";
import IconButton from "@mui/material/IconButton";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import AddNewItemDia from "./AddNewItemDia";
import DeleteDia from "./DeleteDia";
import UpdateDia from "./UpdateDia";
import StreamPlayer from "./StreamPlayer";

export default function RpPage({ rp_info, setSelectedRpInfo }) {
  const [addDia, setAddDia] = React.useState(false);
  const [deleteDia, setDeleteDia] = React.useState(false);
  const [updateDia, setUpdateDia] = React.useState(false);
  const [deleteId, setDeleteId] = React.useState(null);
  const [updateId, setUpdateId] = React.useState(null);
  const [defaultPlayTime, setDefaultPlayTime] = React.useState(1);
  return (
    <Container
      sx={{
        marginTop: "2em",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <AddNewItemDia
        open={addDia}
        setOpen={setAddDia}
        rp_info={rp_info}
        setSelectedRpInfo={setSelectedRpInfo}
      />
      <DeleteDia
        open={deleteDia}
        setOpen={setDeleteDia}
        deleteId={deleteId}
        setDeleteId={setDeleteId}
        rp_id={rp_info.id}
      />
      <UpdateDia
        open={updateDia}
        setOpen={setUpdateDia}
        updateId={updateId}
        setUpdateId={setUpdateId}
        rp_id={rp_info.id}
        defaultPlayTime={defaultPlayTime}
      />
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <div>
            <h1>Name: {rp_info.name}</h1>
            <h1>Mac Address: {rp_info.mac}</h1>

            <List
              sx={{ width: "100%", maxWidth: 360, bgcolor: "background.paper" }}
            >
              {rp_info.playlist.map((item) => {
                return (
                  <ListItem key={item.id}>
                    <ListItemAvatar>
                      <Avatar>
                        {item.type === "video" ? (
                          <VideocamIcon />
                        ) : (
                          <ImageIcon />
                        )}
                      </Avatar>
                    </ListItemAvatar>
                    <ListItemText primary={item.name} secondary={item.type} />
                    <IconButton
                      aria-label="play"
                      onClick={() => {
                        socket.emit("rp-play", {
                          id: rp_info.id,
                          item_id: item.id,
                        });
                      }}
                    >
                      <PlayArrowIcon />
                    </IconButton>
                    <IconButton
                      aria-label="update"
                      onClick={() => {
                        setUpdateId(item.id);
                        setDefaultPlayTime(item.play_time);
                        setUpdateDia(true);
                      }}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      aria-label="delete"
                      onClick={() => {
                        setDeleteDia(true);
                        setDeleteId(item.id);
                      }}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </ListItem>
                );
              })}
            </List>
            <Button
              onClick={() => {
                setAddDia(true);
              }}
            >
              Add New Item
            </Button>
          </div>
        </Grid>
        <Grid item xs={12} sm={6}>
            <StreamPlayer rp_info={rp_info} />
        </Grid>
      </Grid>
    </Container>
  );
}
