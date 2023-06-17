import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogTitle from '@mui/material/DialogTitle';
import { socket } from '../socket';

export default function DraggableDialog({open,setOpen,rp_id,deleteId,setDeleteId}) {

  const handleClose = () => {
    setOpen(false);
  };

  const DeleteItem = () => {
    socket.emit("rp-delete-playlist", {
      id: rp_id,
      item_id: deleteId,
    });
    setDeleteId(null);
    setOpen(false);
  }

  return (
    <div>
      <Dialog
        open={open}
        onClose={handleClose}
      >
        <DialogTitle id="draggable-dialog-title">
          Do you want to delete this item?
        </DialogTitle>
        <DialogActions>
          <Button autoFocus onClick={handleClose}>
            Cancel
          </Button>
          <Button onClick={DeleteItem}>Accept</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
